package models

import scalikejdbc._
import java.time.{ZonedDateTime}
import scalikejdbc.jsr310._

case class Download(
  downloadId: Int,
  downloadDatetime: ZonedDateTime,
  emojiId: Int) {

  def save()(implicit session: DBSession = Download.autoSession): Download = Download.save(this)(session)

  def destroy()(implicit session: DBSession = Download.autoSession): Int = Download.destroy(this)(session)

}


object Download extends SQLSyntaxSupport[Download] {

  override val tableName = "DOWNLOAD"

  override val columns = Seq("DOWNLOAD_ID", "DOWNLOAD_DATETIME", "EMOJI_ID")

  def apply(d: SyntaxProvider[Download])(rs: WrappedResultSet): Download = apply(d.resultName)(rs)
  def apply(d: ResultName[Download])(rs: WrappedResultSet): Download = new Download(
    downloadId = rs.get(d.downloadId),
    downloadDatetime = rs.get(d.downloadDatetime),
    emojiId = rs.get(d.emojiId)
  )

  def opt(d: SyntaxProvider[Download])(rs: WrappedResultSet): Option[Download] =
    rs.intOpt(d.resultName.emojiId).map(_ => Download(d)(rs))

  val d = Download.syntax("d")

  override val autoSession = AutoSession

  def find(downloadId: Int)(implicit session: DBSession = autoSession): Option[Download] = {
    withSQL {
      select.from(Download as d).where.eq(d.downloadId, downloadId)
    }.map(Download(d.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Download] = {
    withSQL(select.from(Download as d)).map(Download(d.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Download as d)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Download] = {
    withSQL {
      select.from(Download as d).where.append(where)
    }.map(Download(d.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Download] = {
    withSQL {
      select.from(Download as d).where.append(where)
    }.map(Download(d.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Download as d).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    downloadDatetime: ZonedDateTime,
    emojiId: Int)(implicit session: DBSession = autoSession): Download = {
    val generatedKey = withSQL {
      insert.into(Download).namedValues(
        column.downloadDatetime -> downloadDatetime,
        column.emojiId -> emojiId
      )
    }.updateAndReturnGeneratedKey.apply()

    Download(
      downloadId = generatedKey.toInt,
      downloadDatetime = downloadDatetime,
      emojiId = emojiId)
  }

  def batchInsert(entities: Seq[Download])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'downloadDatetime -> entity.downloadDatetime,
        'emojiId -> entity.emojiId))
        SQL("""insert into DOWNLOAD(
        DOWNLOAD_DATETIME,
        EMOJI_ID
      ) values (
        {downloadDatetime},
        {emojiId}
      )""").batchByName(params: _*).apply[List]()
    }

  def save(entity: Download)(implicit session: DBSession = autoSession): Download = {
    withSQL {
      update(Download).set(
        column.downloadId -> entity.downloadId,
        column.downloadDatetime -> entity.downloadDatetime,
        column.emojiId -> entity.emojiId
      ).where.eq(column.downloadId, entity.downloadId)
    }.update.apply()
    entity
  }

  def destroy(entity: Download)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Download).where.eq(column.downloadId, entity.downloadId) }.update.apply()
  }

}
