package model

import org.joda.time.DateTime
import play.api.libs.json.{JsString, JsValue, Reads}

/**
 * @author shaiyallin
 * @since 7/28/12
 */

case class SignedInstance(id: String, signed: DateTime, isOwner: Boolean)

object SignedInstance {

  implicit object SignedInstanceReads extends Reads[SignedInstance] {
    def reads(json: JsValue) = SignedInstance(
      id = (json \ "instanceId").as[String],
      signed = new DateTime((json \ "signDate").as[Long]),
      isOwner = (json \ "permissions") match {
        case JsString("OWNER") => true
        case _ => false
      }
    )
  }
}