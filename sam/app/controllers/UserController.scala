package controllers

import model.User
import play.api.libs.functional.syntax.unlift
import play.api.libs.json.{JsPath, Reads, Writes}
import play.api.mvc.Controller

/**
  * Created by Nitin on 3/23/2017.
  */
class UserController extends Controller{

 /* implicit val personWrites: Writes[User] = (
    (JsPath \ "_id").write[String] and
      (JsPath \ "age").write[Int]
    )(unlift(User.unapply))
  implicit val personReads: Reads[User] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "age").read[Int]
    )(User.apply _)*/
}
