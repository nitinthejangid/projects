package Database

/**
  * Created by Nitin on 3/23/2017.
  */

import com.mongodb.casbah.MongoConnection

object MongoFactory1 {
  private val SERVER = "localhost"
  private val PORT   = 27017
  private val DATABASE = "wallet_db"
  private val COLLECTION = "userCollection"
  val connection = MongoConnection(SERVER)
  val collection = connection(DATABASE)(COLLECTION)
}
