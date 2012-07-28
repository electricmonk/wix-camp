package model

import org.specs2.mutable._
import play.api.libs.json.Json
import org.joda.time.DateTime
import com.codahale.jerkson.ParsingException

/**
 * @author shaiyallin
 * @since 7/27/12
 */

class AlbumParseTest extends Specification {

  val discographyJson = """{
      "album_id": 1887072055,
      "release_date": 1127260800,
      "title": "A Prescription for Paper Cuts",
      "downloadable": 2,
      "url": "http:\/\/solsticecoil.bandcamp.com\/album\/a-prescription-for-paper-cuts?pk=564",
      "small_art_url": "http:\/\/f0.bcbits.com\/z\/18\/81\/1881862030-1.jpg",
      "band_id": 2594057871,
      "large_art_url": "http:\/\/f0.bcbits.com\/z\/11\/40\/1140402653-1.jpg",
      "artist": "Solstice Coil"
  }"""

  val albumJson = """{
                      "album_id": 1887072055,
                      "release_date": 1127260800,
                      "title": "A Prescription for Paper Cuts",
                      "downloadable": 2,
                      "url": "http:\/\/solsticecoil.bandcamp.com\/album\/a-prescription-for-paper-cuts?pk=564",
                      "small_art_url": "http:\/\/f0.bcbits.com\/z\/18\/81\/1881862030-1.jpg",
                      "about": "about-text",
                      "band_id": 2594057871,
                      "tracks": [
                        {
                          "album_id": 1887072055,
                          "duration": 253.507,
                          "title": "Photosensivity",
                          "downloadable": 2,
                          "number": 1,
                          "url": "\/track\/photosensivity?pk=564",
                          "band_id": 2594057871,
                          "lyrics": "Decisive force must be used\r\nHe spoke as people fell to the ground\r\nTruth has been strained for too long\r\nNow the muscles had to burst\r\n\r\nWell you seem so surprised\r\nTime and time again\r\nThis report has been compiled\r\nFrom the waters of your friends\r\n\r\n\"I'm ready to die\"\r\nSaid the stapler to the trend\r\nMartyrs are so last week\r\nWatch the box to see what's in\r\n\r\nPeep holes made to fit one's size\r\nNow that's all we can see\r\nOur eyes are stuck between the cracks\r\nStop the skid\r\nRevolution popular\r\nThe check is in the mail\r\nCredence liquefied\r\nIt's so easy to forget\r\n\r\nNod, take our blood, take our blood, only blood, only\r\nKeep growing, keep growing, keep growing, keep growing, \r\nOld, growing old, going bald, staying cold, staying\r\nCash only, cash only, cash only, cash only,\r\nSave, save our ch, save our cha, save our child, save our\r\nSelves, take our shelves, take our bells, leave this place!\r\n\r\nLet me keep the windows shut\r\nKeep the sun away from me\r\nKeep away from the sun",
                          "track_id": 2667870561,
                          "streaming_url": "http:\/\/popplers5.bandcamp.com\/download\/track?enc=mp3-128&fsig=4213f1695e0217ca543160dd621ecd13&id=2667870561&pk=564&stream=1&ts=1343391813.0"
                        },
                        {
                          "album_id": 1887072055,
                          "duration": 93.12,
                          "title": "Caveat Emptor",
                          "downloadable": 2,
                          "number": 2,
                          "url": "\/track\/caveat-emptor?pk=564",
                          "band_id": 2594057871,
                          "track_id": 2720467705,
                          "streaming_url": "http:\/\/popplers5.bandcamp.com\/download\/track?enc=mp3-128&fsig=f177c73876403e0713ac2553fb8d82c6&id=2720467705&pk=564&stream=1&ts=1343391813.0"
                        }
                      ],
                      "large_art_url": "http:\/\/f0.bcbits.com\/z\/11\/40\/1140402653-1.jpg",
                      "credits": "some-credits"
                    }"""

  "Parsing an album" should {
    "fail on empty json" in {
      Album.fromJson(Json.parse("{}")) must throwA[RuntimeException]
    }

    "succeed on json from discography call" in {
      val album = Album.fromJson(Json.parse(discographyJson))
      album.id must beEqualTo(1887072055)
      album.releaseDate must beEqualTo(new DateTime(1127260800))
      album.title must beEqualTo("A Prescription for Paper Cuts")
      album.url must beEqualTo("http://solsticecoil.bandcamp.com/album/a-prescription-for-paper-cuts?pk=564")
      album.smallArtUrl must beEqualTo("http://f0.bcbits.com/z/18/81/1881862030-1.jpg")
      album.largeArtUrl must beEqualTo("http://f0.bcbits.com/z/11/40/1140402653-1.jpg")
      album.credits must beEmpty
      album.about must beEmpty
      album.tracks must beEmpty
    }

    "succeed on json from album call" in {
      val album = Album.fromJson(Json.parse(albumJson))
      album.id must beEqualTo(1887072055)
      album.releaseDate must beEqualTo(new DateTime(1127260800))
      album.title must beEqualTo("A Prescription for Paper Cuts")
      album.url must beEqualTo("http://solsticecoil.bandcamp.com/album/a-prescription-for-paper-cuts?pk=564")
      album.smallArtUrl must beEqualTo("http://f0.bcbits.com/z/18/81/1881862030-1.jpg")
      album.largeArtUrl must beEqualTo("http://f0.bcbits.com/z/11/40/1140402653-1.jpg")
      album.credits must beEqualTo("some-credits")
      album.about must beEqualTo("about-text")
      album.tracks must haveSize(2)
    }
  }
}