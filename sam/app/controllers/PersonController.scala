package controllers

/**
  * Created by Nitin on 3/23/2017.
  */
import model.Person
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc._

import scala.collection.mutable

class PersonController extends Controller {

  val repository: mutable.MutableList[Person] = new mutable.MutableList[Person]()

  implicit val personWrites: Writes[Person] = (
    (JsPath \ "name").write[String] and
      (JsPath \ "age").write[Int]
    )(unlift(Person.unapply))
  implicit val personReads: Reads[Person] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "age").read[Int]
    )(Person.apply _)

  def all = Action {
    Ok(Json.toJson(repository)).as(JSON)
  }

  def insert = Action(BodyParsers.parse.json) { request =>
    val either = request.body.validate[Person]
    either.fold(
      errors => BadRequest("invalid json person"),
      person => {
        repository.+=(person)


        Ok("Inserted successfully!")
      }
    )
  }

}
