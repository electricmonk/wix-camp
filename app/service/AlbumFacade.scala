package service

import model.Album

/**
 * @author shaiyallin
 * @since 7/27/12
 *  http://api.bandcamp.com/api/album/2/info?key=vatnajokull&album_id=1887072055&debug
 */

trait AlbumFacade {
  def bandcamp: BandcampFacade

  def getAlbum(id: Long) = Album.fromJson(bandcamp.album(id))

}