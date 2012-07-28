package model

import org.joda.time.{Duration, DateTime}
import play.api.libs.json._
import com.codahale.jerkson.JsonSnakeCase
import org.codehaus.jackson.annotate.{JsonIgnoreProperties, JsonProperty}
import com.codahale.jerkson.Json._

/**
 *
 * @author shaiyallin
 * @since 7/27/12
 */
case class Album(
  id: Long,
  releaseDate: DateTime,
  title: String,
  about: String,
  credits: String,
  tracks: Seq[Track] = Nil,
  url: String,
  smallArtUrl: String,
  largeArtUrl: String
) {

  lazy val seoTitle = title.toLowerCase.replaceAll("\\s", "-")
}

object Album {

  implicit object AlbumReads extends Reads[Album] {
    def reads(json: JsValue) = Album(
      id = (json \ "album_id").as[Long],
      releaseDate = new DateTime((json \ "release_date").as[Long]),
      title = (json \ "title").as[String],
      about = (json \ "about").asOpt[String].getOrElse(""),
      credits = (json \ "credits").asOpt[String].getOrElse(""),
      url = (json \ "url").as[String],
      smallArtUrl = (json \ "small_art_url").as[String],
      largeArtUrl = (json \ "large_art_url").as[String],
      tracks = (json \ "tracks") match {
        case JsArray(tracks) => tracks.map(Track.fromJson(_))
        case _ => Nil
      }

    )
  }

  def fromJson(album: JsValue) = album.as[Album]
}

case class Track(
  id: Long,
  number: Int,
  duration: Double,
  title: String,
  path: String,
  lyrics: Option[String],
  streamingUrl: String
)

object Track {

  implicit object TrackReads extends Reads[Track] with DefaultReads {
    def reads(json: JsValue) = Track(
      id = (json \ "track_id").as[Long],
      number = (json \ "number").as[Int],
      duration = (json \ "duration").as[Double],
      title = (json \ "title").as[String],
      path = (json \ "url").as[String],
      lyrics = (json \ "lyrics").asOpt[String],
      streamingUrl = (json \ "streaming_url").as[String]
    )
  }

  def fromJson(track: JsValue) = track.as[Track]

}
