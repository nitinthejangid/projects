package controllers



import java.util.Calendar

import Database.{MongoFactory1, _}
import Services.Insert
import com.mongodb.casbah.Imports.{MongoDBObject, _}
import play.api.libs.functional.syntax.unlift
import play.api.libs.json.{JsPath, Json, Reads, Writes}

//import play.api.libs.ws.{WS, WSResponse, WSRequestHolder}
import scala.language.postfixOps
import play.api.libs.ws
import scala.concurrent.Future

import play.api.mvc._


import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
  * Created by Nitin on 3/23/2017.
  */
import Database.{LoginUser, TransResponse, Transaction, User}
import play.api.libs.functional.syntax._
import play.api.libs.json._

import scala.collection.mutable

class Application extends Controller {


  //val repository: mutable.MutableList[User] = new mutable.MutableList[User]()
  //To serialization object
  implicit val userWrites: Writes[User] = (JsPath \ "_id").write[Long].and((JsPath \ "first_name").write[String]).and((JsPath \ "last_name").write[String]).and((JsPath \ "email").write[String]).and((JsPath \ "password").write[String]).and((JsPath \ "total_balance").write[Long])(unlift(User.unapply))
  implicit val userReads: Reads[User] = (JsPath \ "_id").read[Long].and((JsPath \ "first_name").read[String]).and((JsPath \ "last_name").read[String]).and((JsPath \ "email").read[String]).and((JsPath \ "password").read[String]).and((JsPath \ "total_balance").read[Long])(User.apply _)

  implicit val LoginUserWrites: Writes[LoginUser] = (JsPath \ "email").write[String].and((JsPath \ "password").write[String]).and((JsPath \ "token").write[String])(unlift(LoginUser.unapply))
  implicit val LoginUserReads: Reads[LoginUser] = (JsPath \ "email").read[String].and((JsPath \ "password").read[String]).and((JsPath \ "token").read[String])(LoginUser.apply _)

  implicit val TransactionWrites: Writes[Transaction] = (JsPath \ "access_token").write[String].and((JsPath \ "transaction_type").write[String]).and((JsPath \ "_type").write[String]).and((JsPath \ "amount").write[Long]).and((JsPath \ "remarks").write[String])(unlift(Transaction.unapply))
  implicit val TransactionReads: Reads[Transaction] = (JsPath \ "access_token").read[String].and((JsPath \ "transaction_type").read[String]).and((JsPath \ "_type").read[String]).and((JsPath \ "amount").read[Long]).and((JsPath \ "remarks").read[String])(Transaction.apply _)

  implicit val TransResponseWrites: Writes[TransResponse] = (JsPath \ "status").write[String].and((JsPath \ "transection_id").write[String])(unlift(TransResponse.unapply))
  implicit val TransResponseReads: Reads[TransResponse] = (JsPath \ "status").read[String].and((JsPath \ "transection_id").read[String])(TransResponse.apply _)

  implicit val TransferWrites: Writes[Transfer] = (JsPath \ "access_token").write[String].and((JsPath \ "mobile").write[String]).and((JsPath \ "email").write[String]).and((JsPath \ "amount").write[Long])(unlift(Transfer.unapply))
  implicit val TransferReads: Reads[Transfer] = (JsPath \ "access_token").read[String].and((JsPath \ "mobile").read[String]).and((JsPath \ "email").read[String]).and((JsPath \ "amount").read[Long])(Transfer.apply _)

  def getAll = Action {
    val conn = MongoFactory.collection
    var q = MongoDBObject("first_name" -> "manoj")
    val document = conn.findOne(q)
    println()
    val json = "[%s]".format(
      List(document.get).mkString(",")
    )

    Ok(Json.toJson(json)).as(JSON)
  }
  def insert = Action(BodyParsers.parse.json) { request =>
    val either = request.body.validate[User]
    //{ "_id": 1, "first_name": "manoj", "last_name": "jangid", "email": "manoj@gmail.com", "password": "abc", "total_balance": 23 }
    either.fold(
      errors => BadRequest("invalid json person"),
      user => {
        println("Inserting data in db")
        Insert.saveStock(user)
        println("Insterted ")

        Ok("Inserted successfully!")
        Ok(Json.toJson(user)).as(JSON)
        //{ "_id": 1, "first_name": "manoj", "last_name": "jangid", "email": "manoj@gmail.com", "password": "abc", "total_balance": 23 }
      }
    )
  }


  def index = Action {
    val apple = Stock("AAPL", 600)
    /*Insert.saveStock(apple)*/

    println("inserted successfully")

    Ok("working")
  }
  //{ "email": "nitin", "password": "asdfdggadsg" }
  def loginUser = Action(BodyParsers.parse.json) { request =>
    val either = request.body.validate[LoginUser]
    //{ "first_name": "nitin", "password": "abc" }
    either.fold(
      errors => BadRequest("invalid json person"),
      loginUser => {
          val first_name = loginUser.first_name
          println(first_name)
          val password = loginUser.password
          println("create token----")

          val conn = MongoFactory.collection
          var q = MongoDBObject("email" -> first_name)
          val document = conn.findOne(q)
          println(document)
        val passwordDB = document.map( _.as[String]("password").toString())
        val first_nameDB = document.map( _.as[String]("email").toString())

        println(passwordDB)
        println(first_nameDB)
          if(document.toString().equalsIgnoreCase("None"))
          {
            Ok("{\n\t\"status\": \"fail\",\n\t\"error\": \"User name not exist\"\n}").as("application/json")
          }
          else{
            val passwordDB = document.map( _.as[String]("password").toString())

            if(passwordDB.get.equals(password))
              {
                val md = java.security.MessageDigest.getInstance("SHA-1")
                var token = play.api.libs.Codecs.sha1(md.digest("Foo".getBytes))
                println(token)
                Ok("{\n\t\"status\": \"success\",\n\t\"access_token\": \"HJNEKRHBNS567HKN653HLB\"\n}").as("application/json")
              }
            else{
              Ok("{\n\t\"status\": \"fail\",\n\t\"error\": \"Wrong password\"\n}").as("application/json")
            }
        }

          //{ "_id": 1, "first_name": "manoj", "last_name": "jangid", "email": "manoj@gmail.com", "password": "abc", "total_balance": 23 }
      }
    )
  }


  def getTransaction = Action(BodyParsers.parse.json) { request =>
    val either = request.body.validate[Transaction]
    either.fold(
      errors => BadRequest("invalid json person"),
      transaction => {
        //repository.+=(person)
        val token = transaction.access_token
        val query = MongoDBObject("token_id"-> token)
        val col = MongoFactory2.collection.findOne(query)
        if(col.toString().equalsIgnoreCase("None")){
          Ok("{\n\t\"status\": \"fail\",\n\t\"error\": \"Invalid (or) expired access token\"\n}").as("application/json")
        }else {
          val data = col.map(_.as[String]("user").toString())
          println("_id  :" + data.get)
          val query2 = MongoDBObject("_id" -> data.get)
          val col2 = MongoFactory1.collection.findOne(query2)

          val tb = col2.map(_.as[Double]("total_balance"))
          val uc = col2.map(_.as[Double]("user_cash"))
          val pc = col2.map(_.as[Double]("promotional_cash"))
          val email = col2.map(_.as[String]("email")).get
          val lock = col2.map(_.as[Boolean]("lock"))

          println(email)
          //checking for lock or unlock
          if(lock.get == false) {

            //Call for lock trasection
            lockTransection(email)
            if (transaction.transaction_type.equalsIgnoreCase("Debit")) {
              if (transaction._type.equalsIgnoreCase("cash")) {
                if (uc.get < transaction.amount) {

                  //Call for unlock trasection
                  UnlockTransection(email)

                  Ok("{\n\t\"status\": \"fail\",\n\t\"error\": \"Balance Not enough to Debit\"\n}").as("application/json")
                }
                else {

                  //{ "access_token": "HDJLPWIERJNSLJ", "transaction_type": "Debit", "_type": "cash", "amount": 10, "remarks": "done" }
                  val after_debit_user = uc.get - transaction.amount
                  val after_debit_total = tb.get - transaction.amount
                  //val change = Transaction(token, transaction.transaction_type, transaction._type, after_debit_total, transaction.remarks)
                  //val dbvalue = Common.buildMongoDbObject_cash(change)
                  val uuid = java.util.UUID.randomUUID().toString.replace("-", "").toUpperCase
                  val upval = MongoDBObject(
                    "$set" -> MongoDBObject("total_balance" -> after_debit_total,
                      "user_cash" -> after_debit_user, "last_transaction_id" -> uuid)
                  )

                  val col3 = MongoFactory1.collection.update(query2, upval)


                  println(uuid)

                  val respo = TransResponse("Success", uuid)
                  val repository: mutable.MutableList[TransResponse] = new mutable.MutableList[TransResponse]()
                  repository += respo

                  //Call for unlock trasection
                  UnlockTransection(email)

                  Ok(Json.toJson(repository)).as("application/json")
                }
              }
              else {
                if (pc.get < transaction.amount) {

                  //----Unlock trasection code
                  val lockFalse = MongoDBObject(
                    "$set" -> MongoDBObject("lock" -> false)
                  )
                  val updateTrue = MongoFactory1.collection.update(query2, lockFalse)
                  //-----

                  Ok("{\n\t\"status\": \"Success\",\n\t\"error\": \"Balance Not enough to Debit\"\n}").as("application/json")
                }
                else {
                  //{ "access_token": "HDJLPWIERJNSLJ", "transaction_type": "Debit", "_type": "promotional", "amount": 10, "remarks": "done" }
                  val after_debit_promotion = pc.get - transaction.amount
                  val after_debit_total = tb.get - transaction.amount
                  val uuid = java.util.UUID.randomUUID().toString.replace("-", "").toUpperCase
                  val upval = MongoDBObject(
                    "$set" -> MongoDBObject("total_balance" -> after_debit_total,
                      "promotional_cash" -> after_debit_promotion, "last_transaction_id" -> uuid)
                  )

                  val col3 = MongoFactory1.collection.update(query2, upval)


                  val respo = TransResponse("Success", uuid)
                  val repository: mutable.MutableList[TransResponse] = new mutable.MutableList[TransResponse]()
                  repository += respo

                  //Call for unlock trasection
                  UnlockTransection(email)

                  Ok(Json.toJson(repository)).as("application/json")
                }
              }
            }
            else {
              if (transaction._type.equalsIgnoreCase("cash")) {
                //{ "access_token": "HDJLPWIERJNSLJ", "transaction_type": "credit", "_type": "cash", "amount": 10, "remarks": "done" }
                val after_credit_user = uc.get + transaction.amount
                val after_credit_total = tb.get + transaction.amount
                val uuid = java.util.UUID.randomUUID().toString.replace("-", "").toUpperCase
                val upval = MongoDBObject(
                  "$set" -> MongoDBObject("total_balance" -> after_credit_total,
                    "user_cash" -> after_credit_user, "last_transaction_id" -> uuid)
                )

                val col3 = MongoFactory1.collection.update(query2, upval)


                println(uuid)

                val respo = TransResponse("Success", uuid)
                val repository: mutable.MutableList[TransResponse] = new mutable.MutableList[TransResponse]()
                repository += respo

                //Call for unlock trasection
                UnlockTransection(email)

                Ok(Json.toJson(repository)).as("application/json")
              } else {
                val after_credit_promotion = pc.get + transaction.amount
                val after_credit_total = tb.get + transaction.amount
                  val uuid = java.util.UUID.randomUUID().toString.replace("-", "").toUpperCase
                val upval = MongoDBObject(
                  "$set" -> MongoDBObject("total_balance" -> after_credit_total,
                    "promotional_cash" -> after_credit_promotion, "last_transaction_id" -> uuid)
                )

                val col3 = MongoFactory1.collection.update(query2, upval)


                println(uuid)

                val respo = TransResponse("Success", uuid)
                val repository: mutable.MutableList[TransResponse] = new mutable.MutableList[TransResponse]()
                repository += respo

                //Call for unlock trasection
                UnlockTransection(email)

                Ok(Json.toJson(repository)).as("application/json")
              }
            }

          }
          else{
            Ok("{\n\t\"status\": \"fail\",\n\t\"error\": \"Wallet is locked! you can not do parallel trasection.\"}").as("application/json")
          }


          // Ok(json).as("application/json")
        }
        //Ok(col.toString())
      }
    )
  }

  //Lock Transection API Auther: Nitin
  def lockTransection(email: String) {

    val queryEmail = MongoDBObject("email" -> email)
    val document = MongoFactory1.collection.findOne(queryEmail)
    val lock = document.map(_.as[Boolean]("lock"))

    val last_lock_time = Calendar.getInstance().getTime().toString()

      //---Lock trasection code
      val lockTrue = MongoDBObject(
        "$set" -> MongoDBObject("lock" -> true, "last_lock_time" -> last_lock_time)
      )
      MongoFactory1.collection.findAndModify(queryEmail, lockTrue)


    //Ok("ok").as(JSON)
  }

  def UnlockTransection(email: String) {

    val queryEmail = MongoDBObject("email" -> email)
    val document = MongoFactory1.collection.findOne(queryEmail)
    val lock = document.map(_.as[Boolean]("lock"))

      //---Lock trasection code
      val lockTrue = MongoDBObject(
        "$set" -> MongoDBObject("lock" -> false)
      )
      MongoFactory1.collection.findAndModify(queryEmail, lockTrue)


    //Ok("ok").as(JSON)
  }

  def transferMoney = Action(BodyParsers.parse.json) { request =>
    val either = request.body.validate[Transfer]
    either.fold(
      errors => BadRequest("invalid json person"),
      transfer => {

        val senderToken = transfer.access_token
        val receiverMobile = transfer.mobile
        val receiverEmail = transfer.email
        val amountToTransfer = transfer.amount
        println(senderToken + "---" + receiverMobile + "---" + receiverEmail + "---" + amountToTransfer)


        //sender account
        val querySession = MongoDBObject("token_id"-> senderToken)
        val documentSession = MongoFactory2.collection.findOne(querySession)
        val userSession = documentSession.map(_.as[String]("user").toString())

        val queryUserCollection = MongoDBObject("_id" -> userSession.get)
        val documentUserCollection = MongoFactory1.collection.findOne(queryUserCollection)
        val totalBalaceSender = documentUserCollection.map(_.as[Double]("total_balance")).get
        val userCashSender = documentUserCollection.map(_.as[Double]("user_cash")).get
        val senderEmail = documentUserCollection.map(_.as[String]("email")).get
        val senderLock = documentUserCollection.map(_.as[Boolean]("lock")).get
        //-----

        //Receiver account
        val query = MongoDBObject("mobile"-> receiverMobile)
        val document1 = MongoFactory1.collection.findOne(query)
        val total_balanceReceiver = document1.map( _.as[Double]("total_balance"))
        val user_cashReceiver = document1.map( _.as[Double]("user_cash"))
        val ReceiverLock = document1.map(_.as[Boolean]("lock")).get
        //-----


        println(document1)
        if(document1.toString().equalsIgnoreCase("None")) {
          Ok("{\n\t\"status\": \"fail\",\n\t\"error\": \"User is not valid!\"\n}").as("application/json")
        }
        else {
          if(senderLock == false && ReceiverLock == false) {
            //Call for lock trasection
            lockTransection(senderEmail)
            lockTransection(receiverEmail)

            //updating the credit amount in Account2
            val after_credit_total = total_balanceReceiver.get + amountToTransfer
            val after_credit_user = user_cashReceiver.get + amountToTransfer

            println(after_credit_total + "  gfhgjg-----  " + after_credit_user)
            val update = MongoDBObject(
              "$set" -> MongoDBObject("total_balance" -> after_credit_total,
                "user_cash" -> after_credit_user)
            )
            val updatedb = MongoFactory1.collection.findAndModify(query, update)

            //---

            //updating the debit amount in Account1
            val after_debit_user = userCashSender - amountToTransfer
            val after_debit_total = totalBalaceSender - amountToTransfer

            val uuid = java.util.UUID.randomUUID().toString.replace("-", "").toUpperCase
            val upval = MongoDBObject(
              "$set" -> MongoDBObject("total_balance" -> after_debit_total,
                "user_cash" -> after_debit_user, "last_transaction_id" -> uuid)
            )

            val col3 = MongoFactory1.collection.findAndModify(queryUserCollection, upval)
            //-----


            //Calling Trasection RestApi
            /*var response: String = "";
              val payload = "{\"access_token\" :"+"\""+token+"\" , \"transaction_type\" :"+ "\"+Debit+\", \"_type\" :"+"\"\"+cash+\", \"amount\" :"+"\"\""+amount+"\",\"remarks\" :"+"\"\"+Done+\" }"
              //{ "access_token": "HDJLPWIERJNSLJ", "transaction_type": "Debit", "_type": "cash", "amount": 10, "remarks": "done" }

              val holder : WSRequestHolder = WS.url("http://localhost:9000/transection")
              //val promiseString : Future[WSResponse] = WS.url("http://localhost:9000/transection").withHeaders("Content-Type" -> "application/json")
                .post(payload)

              val complexHolder : WSRequestHolder = holder.withHeaders("Content-Type" -> "application/json")
              val futureResponse : Future[Response] = complexHolder.post()
              val res = Await.result(promiseString,Duration.Inf)

              println("XXXXX..."+res.body)

              if(res.body.equals("")){
                println("if coming")
                Ok("not working")
              }*/


            //Call for unlock trasection
            UnlockTransection(receiverEmail)
            UnlockTransection(senderEmail)

            val respo = TransResponse("Success", uuid)
            val repository: mutable.MutableList[TransResponse] = new mutable.MutableList[TransResponse]()
            repository += respo
            Ok(Json.toJson(repository)).as("application/json")

          }
          else{
            Ok("{\n\t\"status\": \"fail\",\n\t\"error\": \"Wallet is locked! due to some parallel trasection.\"}").as("application/json")
          }
        }
      }
    )
  }
}
