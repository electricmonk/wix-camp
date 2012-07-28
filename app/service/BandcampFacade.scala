package service

import play.api.libs.ws.WS
import java.util.concurrent.TimeUnit

/**
 * @author shaiyallin
 * @since 7/27/12
 */

trait BandcampFacade {

  def apiKey: String
  def timeoutInMillis: Long

  def discography(bandId: Long) = call("http://api.bandcamp.com/api/band/3/discography?key=%s&band_id=%s".format(apiKey, bandId))

  def album(id: Long) = call("http://api.bandcamp.com/api/album/2/info?key=%s&album_id=%s".format(apiKey, id))

  private def call(url: String) =
    WS.url(url)
      .get()
      .await(timeoutInMillis, TimeUnit.MILLISECONDS)
      .get
      .json
}

class DefaultBandcampFacade(val timeoutInMillis: Long = 3000, val apiKey: String = "vatnajokull") extends BandcampFacade