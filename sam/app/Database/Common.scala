package Database

/**
  * Created by Nitin on 3/23/2017.
  */
import com.mongodb.casbah.Imports._

case class Stock (symbol: String, price: Double)

case class User(_id: Long, first_name: String, last_name: String, email: String, password: String, total_balance: Long)
case class LoginUser( first_name: String, password: String, token: String)

case class Transaction( access_token: String, transaction_type: String, _type: String, amount: Long, remarks: String)
case class TransResponse(status: String, transection_id: String)

case class Transfer(access_token: String, mobile: String, email: String, amount: Long)

//case class LockTransec(email: String)

object Common {
  /**
    * Convert a Stock object into a BSON format that MongoDb can store.
    */
  /*def buildMongoDbObject(stock: Stock): MongoDBObject = {
    val builder = MongoDBObject.newBuilder
    builder += "symbol" -> stock.symbol
    builder += "price" -> stock.price
    builder.result
  }*/
  def buildMongoDbObject(user: User): MongoDBObject = {
    val builder = MongoDBObject.newBuilder
    builder += "_id" -> user._id
    builder += "first_name" -> user.first_name
    builder += "last_name" -> user.last_name
    builder += "email" -> user.email
    builder += "password" -> user.password
    builder += "total_balance" -> user.total_balance
    builder.result
  }

  def buildMongoDbObject_cash(transaction: Transaction): MongoDBObject = {
    val builder = MongoDBObject.newBuilder
    builder += "first_name" -> "Nitin"
    builder += "first_name" -> "Nitin"
    builder += "last_name" ->"jangid"
    builder += "email" ->"nitin@gmail.com"
    builder += "password" ->"abc"
    builder += "total_balance" -> transaction.amount
    builder.result
  }
}
