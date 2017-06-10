package Services

/**
  * Created by Nitin on 3/23/2017.
  */
import Database.{MongoFactory, User}
import com.mongodb.casbah.Imports._


object Find {

  def findStock {
    val conn = MongoFactory.collection
    /*
    var wallet_db = conn("wallet_db").find()
*/

    val collection = conn.find();
    println("\n--- All Stocks ---")
    println("gettingnnnnnnnnnn")
    collection.foreach(println);
    println("done")

    //return collection

    println("\n--- .findOne() ---")

    var q = MongoDBObject("first_name" -> "manoj")
    val document = conn.findOne(q)
    println(document)

    /* println("\n--- .find ---")
    user = collection.find("price" $gt 500)
    user.foreach(println)
/**/*/
    //conn.close
    return document
  }
}