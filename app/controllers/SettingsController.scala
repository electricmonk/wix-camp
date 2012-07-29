package controllers

import play.api.mvc.{Action, Controller}
import play.api.data.Forms._
import play.api.data._
import java.net.URI
import model.Instance
import service.{JdbcInstanceDao, InstanceDao}

/**
 * @author shaiyallin
 * @since 7/29/12
 */
trait SettingsController extends Controller {

  def dao: InstanceDao

  val form = Form(
//    "bandcampUrl" -> text.verifying(validUrl(_))      TODO get band id by url
    "bandId" -> longNumber
  )

  val instance = Instance(id = "abcd", bandId = 2594057871)

  def show = Action {
    val filledForm = form.fill(instance.bandId)
    Ok(views.html.settings(filledForm))
  }

  def save = Action { implicit request =>
    form.bindFromRequest.fold(
      errors => BadRequest(views.html.settings(errors)),
      bandId => {
        dao.update(Instance(instance.id, bandId))
        Redirect(routes.SettingsController.show)
      }
    )
  }

  private def validUrl(text: String) =
    try {
      new URI(text)
      true
    } catch {
      case _ => false
    }
}

object SettingsController extends SettingsController {
  val dao = new JdbcInstanceDao
}