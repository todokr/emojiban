package models

import scalikejdbc._
import java.time.{ZonedDateTime}
import scalikejdbc.jsr310._

case class Evaluation(
  evaluationId: Int,
  value: String,
  evaluatedDatetime: ZonedDateTime,
  emojiId: Int) {

  def save()(implicit session: DBSession = Evaluation.autoSession): Evaluation = Evaluation.save(this)(session)

  def destroy()(implicit session: DBSession = Evaluation.autoSession): Int = Evaluation.destroy(this)(session)

}


object Evaluation extends SQLSyntaxSupport[Evaluation] {

  override val tableName = "EVALUATION"

  override val columns = Seq("EVALUATION_ID", "VALUE", "EVALUATED_DATETIME", "EMOJI_ID")

  def apply(e: SyntaxProvider[Evaluation])(rs: WrappedResultSet): Evaluation = autoConstruct(rs, e)
  def apply(e: ResultName[Evaluation])(rs: WrappedResultSet): Evaluation = autoConstruct(rs, e)

  val e = Evaluation.syntax("e")

  override val autoSession = AutoSession

  def find(evaluationId: Int)(implicit session: DBSession = autoSession): Option[Evaluation] = {
    withSQL {
      select.from(Evaluation as e).where.eq(e.evaluationId, evaluationId)
    }.map(Evaluation(e.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Evaluation] = {
    withSQL(select.from(Evaluation as e)).map(Evaluation(e.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Evaluation as e)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Evaluation] = {
    withSQL {
      select.from(Evaluation as e).where.append(where)
    }.map(Evaluation(e.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Evaluation] = {
    withSQL {
      select.from(Evaluation as e).where.append(where)
    }.map(Evaluation(e.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Evaluation as e).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    value: String,
    evaluatedDatetime: ZonedDateTime,
    emojiId: Int)(implicit session: DBSession = autoSession): Evaluation = {
    val generatedKey = withSQL {
      insert.into(Evaluation).namedValues(
        column.value -> value,
        column.evaluatedDatetime -> evaluatedDatetime,
        column.emojiId -> emojiId
      )
    }.updateAndReturnGeneratedKey.apply()

    Evaluation(
      evaluationId = generatedKey.toInt,
      value = value,
      evaluatedDatetime = evaluatedDatetime,
      emojiId = emojiId)
  }

  def batchInsert(entities: Seq[Evaluation])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'value -> entity.value,
        'evaluatedDatetime -> entity.evaluatedDatetime,
        'emojiId -> entity.emojiId))
        SQL("""insert into EVALUATION(
        VALUE,
        EVALUATED_DATETIME,
        EMOJI_ID
      ) values (
        {value},
        {evaluatedDatetime},
        {emojiId}
      )""").batchByName(params: _*).apply[List]()
    }

  def save(entity: Evaluation)(implicit session: DBSession = autoSession): Evaluation = {
    withSQL {
      update(Evaluation).set(
        column.evaluationId -> entity.evaluationId,
        column.value -> entity.value,
        column.evaluatedDatetime -> entity.evaluatedDatetime,
        column.emojiId -> entity.emojiId
      ).where.eq(column.evaluationId, entity.evaluationId)
    }.update.apply()
    entity
  }

  def destroy(entity: Evaluation)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Evaluation).where.eq(column.evaluationId, entity.evaluationId) }.update.apply()
  }

}
