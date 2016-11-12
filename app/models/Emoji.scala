package models

import scalikejdbc._
import java.time.{ZonedDateTime}
import scalikejdbc.jsr310._

import scala.util.Try

case class Emoji(
  emojiId: Int,
  imagePath: String,
  createdDatetime: ZonedDateTime,
  userId: Int,
  evaluation: Seq[Evaluation] = Nil,
  name: Seq[Name] = Nil) {

  def save()(implicit session: DBSession = Emoji.autoSession): Emoji = Emoji.save(this)(session)

  def destroy()(implicit session: DBSession = Emoji.autoSession): Int = Emoji.destroy(this)(session)

}


object Emoji extends SQLSyntaxSupport[Emoji] {

  override val tableName = "EMOJI"

  override val columns = Seq("EMOJI_ID", "IMAGE_PATH", "CREATED_DATETIME", "USER_ID")

  def apply(e: SyntaxProvider[Emoji])(rs: WrappedResultSet): Emoji = apply(e.resultName)(rs)
  def apply(e: ResultName[Emoji])(rs: WrappedResultSet): Emoji = new Emoji(
    emojiId = rs.get(e.emojiId),
    imagePath = rs.get(e.imagePath),
    createdDatetime = rs.get(e.createdDatetime),
    userId = rs.get(e.userId)
  )

  val (e, ev, n) = (Emoji.syntax, Evaluation.syntax, Name.syntax)

  override val autoSession = AutoSession

  def find(emojiId: Int)(implicit session: DBSession = autoSession): Option[Emoji] = {
    withSQL {
      select.from(Emoji as e)
        .leftJoin(Evaluation as ev).on(e.emojiId, ev.emojiId)
        .leftJoin(Name as n).on(e.emojiId, n.emojiId)
        .where.eq(e.emojiId, emojiId)
    }
      .one(Emoji(e))
      .toManies(
        rs => Evaluation.opt(ev)(rs),
        rs => Name.opt(n)(rs)
      )
      .map{ (emoji, evaluation, name) => emoji.copy(evaluation = evaluation, name = name) }
      .single
      .apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Emoji] = {
    withSQL {
      select.from(Emoji as e)
        .leftJoin(Evaluation as ev).on(e.emojiId, ev.emojiId)
        .leftJoin(Name as n).on(e.emojiId, n.emojiId)
    }
      .one(Emoji(e))
      .toManies(
        rs => Evaluation.opt(ev)(rs),
        rs => Name.opt(n)(rs)
      )
      .map{ (emoji, evaluation, name) => emoji.copy(evaluation = evaluation, name = name) }
      .list
      .apply()
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
    userId: Int,
    names: Seq[String])(implicit session: DBSession = autoSession): Emoji = {

    //val db = DB(session.connection)
    // TODO transaction

    val generatedEmojiId = withSQL {
      insert.into(Emoji).namedValues(
        column.imagePath -> imagePath,
        column.createdDatetime -> createdDatetime,
        column.userId -> userId
      )
    }.updateAndReturnGeneratedKey.apply()

    val createdNames = names.map { name =>
      Name.create(name, generatedEmojiId.toInt)
    }

    Emoji(
      emojiId = generatedEmojiId.toInt,
      imagePath = imagePath,
      createdDatetime = createdDatetime,
      userId = userId,
      name = createdNames)
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
