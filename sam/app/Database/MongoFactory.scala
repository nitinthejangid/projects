package Database

/**
  * Created by Nitin on 3/23/2017.
  */

import com.mongodb.casbah.MongoConnection

object MongoFactory {
  private val SERVER = "localhost"
  private val PORT   = 27017
  private val DATABASE = "wallet_db"
  private val COLLECTION = "stocks"
  val connection = MongoConnection(SERVER)
  val collection = connection(DATABASE)(COLLECTION)
}
