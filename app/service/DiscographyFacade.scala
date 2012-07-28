package service

import play.api.libs.ws.WS
import java.util.concurrent.TimeUnit
import model.Album
import play.api.libs.json.JsArray

/**
 * @author shaiyallin
 * @since 7/27/12
 *
 * http://api.bandcamp.com/api/band/3/discography?key=vatnajokull&band_id=2594057871&debug
 */

trait DiscographyFacade {

  def bandcamp: BandcampFacade

  def getAlbums(bandId: Long) = {
    val json = bandcamp.discography(bandId)
    (json \ "discography") match {
      case JsArray(albums) => albums.map(Album.fromJson(_))
      case _ => Nil
    }
  }

}