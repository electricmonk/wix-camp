package service

import model.Instance
import anorm._
import anorm.SqlParser._
import play.api.db.DB

/**
 * @author shaiyallin
 * @since 7/29/12
 */

trait InstanceDao {

  def load(id: String): Instance
  def create(instance: Instance)
  def update(instance: Instance)
}

class JdbcInstanceDao extends InstanceDao {

  val instance = {
    get[String]("id") ~
    get[Long]("band_id") map {
      case id~bandId => Instance(id, bandId)
    }
  }.single

  def load(id: String) = DB.withConnection { implicit c =>
    SQL("select * from instances where id = {id}").on("id" -> id).as(instance)
  }

  def create(instance: Instance) {
    DB.withConnection { implicit c =>
      SQL("insert into instances (id, band_id) values ({id}, {band_id})").on("id" -> instance.id, "band_id" -> instance.bandId)
    }
  }

  def update(instance: Instance) {
    DB.withConnection { implicit c =>
      SQL("update instances set band_id = {band_id} where id = {id}").on("id" -> instance.id, "band_id" -> instance.bandId)
    }
  }
}