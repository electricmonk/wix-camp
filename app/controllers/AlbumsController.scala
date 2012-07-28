package controllers

import play.api.mvc.{Action, Controller}
import service.{DefaultBandcampFacade, DiscographyFacade, AlbumFacade}

/**
 * @author shaiyallin
 * @since 7/28/12
 */

trait AlbumsController extends Controller {
  def albumFacade: AlbumFacade
  def discographyFacade: DiscographyFacade

  val bandId = 2594057871l

  def index = Action {
    Ok(views.html.discography(discographyFacade.getAlbums(bandId)))
  }

  def album(id: Long, title: String) = Action {
    Ok(views.html.album(albumFacade.getAlbum(id)))
  }
}

object AlbumsController extends AlbumsController {

  val bandcampFacade = new DefaultBandcampFacade

  val albumFacade = new AlbumFacade {
    def bandcamp = bandcampFacade
  }

  val discographyFacade = new DiscographyFacade {
    def bandcamp = bandcampFacade
  }
}