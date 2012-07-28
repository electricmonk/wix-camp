package service

import org.apache.commons.codec.binary.Base64
import javax.crypto.spec.SecretKeySpec
import javax.crypto.Mac
import collection.mutable
import play.api.libs.json.Json
import model.SignedInstance

/**
 * @author shaiyallin
 * @since 7/28/12
 */

class SignedInstanceReader(secret: String) {

  val base64 = new Base64(256, null, true)
  val secretKeySpec = new SecretKeySpec(secret.getBytes, "HMACSHA256")
  val mac = Mac.getInstance("HMACSHA256")
  mac.init(secretKeySpec)

  def parse(instance: String) = {
    instance.split('.').toList match {
      case List(signature, encodedJson) =>
        val wixSig: mutable.WrappedArray[Byte] = base64.decode(signature)
        val mySig: mutable.WrappedArray[Byte] = mac.doFinal(encodedJson.getBytes)
        if (wixSig == mySig)
          Some(Json.parse(new String(base64.decode(encodedJson))).as[SignedInstance])
        else
          None
      case _ => None
    }
  }
}