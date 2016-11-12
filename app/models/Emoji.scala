package models

import scalikejdbc._
import java.time.{ZonedDateTime}
import scalikejdbc.jsr310._

case class Emoji(
  emojiId: Int,
  imagePath: String,
  createdDatetime: ZonedDateTime,
  userId: Int) {

  def save()(implicit session: DBSession = Emoji.autoSession): Emoji = Emoji.save(this)(session)

  def destroy()(implicit session: DBSession = Emoji.autoSession): Int = Emoji.destroy(this)(session)

}


object Emoji extends SQLSyntaxSupport[Emoji] {

  override val tableName = "EMOJI"

  override val columns = Seq("EMOJI_ID", "IMAGE_PATH", "CREATED_DATETIME", "USER_ID")

  def apply(e: SyntaxProvider[Emoji])(rs: WrappedResultSet): Emoji = autoConstruct(rs, e)
  def apply(e: ResultName[Emoji])(rs: WrappedResultSet): Emoji = autoConstruct(rs, e)

  val e = Emoji.syntax("e")

  override val autoSession = AutoSession

  def find(emojiId: Int)(implicit session: DBSession = autoSession): Option[Emoji] = {
    withSQL {
      select.from(Emoji as e).where.eq(e.emojiId, emojiId)
    }.map(Emoji(e.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Emoji] = {
    withSQL(select.from(Emoji as e)).map(Emoji(e.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Emoji as e)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Emoji] = {
    withSQL {
      select.from(Emoji as e).where.append(where)
    }.map(Emoji(e.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Emoji] = {
    withSQL {
      select.from(Emoji as e).where.append(where)
    }.map(Emoji(e.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Emoji as e).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    imagePath: String,
    createdDatetime: ZonedDateTime,
    userId: Int)(implicit session: DBSession = autoSession): Emoji = {
    val generatedKey = withSQL {
      insert.into(Emoji).namedValues(
        column.imagePath -> imagePath,
        column.createdDatetime -> createdDatetime,
        column.userId -> userId
      )
    }.updateAndReturnGeneratedKey.apply()

    Emoji(
      emojiId = generatedKey.toInt,
      imagePath = imagePath,
      createdDatetime = createdDatetime,
      userId = userId)
  }

  def batchInsert(entities: Seq[Emoji])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'imagePath -> entity.imagePath,
        'createdDatetime -> entity.createdDatetime,
        'userId -> entity.userId))
        SQL("""insert into EMOJI(
        IMAGE_PATH,
        CREATED_DATETIME,
        USER_ID
      ) values (
        {imagePath},
        {createdDatetime},
        {userId}
      )""").batchByName(params: _*).apply[List]()
    }

  def save(entity: Emoji)(implicit session: DBSession = autoSession): Emoji = {
    withSQL {
      update(Emoji).set(
        column.emojiId -> entity.emojiId,
        column.imagePath -> entity.imagePath,
        column.createdDatetime -> entity.createdDatetime,
        column.userId -> entity.userId
      ).where.eq(column.emojiId, entity.emojiId)
    }.update.apply()
    entity
  }

  def destroy(entity: Emoji)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Emoji).where.eq(column.emojiId, entity.emojiId) }.update.apply()
  }

}
