package com.navneetgupta.fileupload.protocols

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._
import java.util.Date
import scala.math.BigDecimal
import com.navneetgupta.fileupload.command.UploadFile
import com.navneetgupta.fileupload.command.FileDetails

trait FileUploadJsonProtocol extends SprayJsonSupport with DefaultJsonProtocol {
  implicit object AnyJsonFormat extends JsonFormat[Any] {
    override def write(x: Any) = x match {
      case n: Int                   => JsNumber(n)
      case s: String                => JsString(s)
      case b: Boolean if b == true  => JsTrue
      case b: Boolean if b == false => JsFalse
    }
    override def read(value: JsValue) = value match {
      case JsNumber(n) => n.intValue()
      case JsString(s) => s
      case JsTrue      => true
      case JsFalse     => false
    }
  }

  implicit val uploadFileDetails = jsonFormat3(FileDetails.apply)
}
