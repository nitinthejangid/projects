package Services

/**
  * Created by Nitin on 3/23/2017.
  */
import com.mongodb.casbah.Imports._
import Database.{Common, MongoFactory, Stock}
import Database.User

object Insert {

  // create some Stock instances
 // val apple = Stock("AAPL", 600)
  //val google = Stock("GOOG", 650)
  //val netflix = Stock("NFLX", 60)

  // save them to the mongodb database
  //saveStock(apple)
  //saveStock(google)
  //saveStock(netflix)

  // our 'save' method
  /*def saveStock(stock: Stock) {
    val mongoObj = Common.buildMongoDbObject(stock)
    MongoFactory.collection.save(mongoObj)
  }*/

  def saveStock(user: User) {
    val mongoObj = Common.buildMongoDbObject(user)
    MongoFactory.collection.save(mongoObj)
  }

}
