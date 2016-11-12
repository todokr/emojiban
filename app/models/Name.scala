package models

import scalikejdbc._

case class Name(
  name: String,
  emojiId: Int) {

  def save()(implicit session: DBSession = Name.autoSession): Name = Name.save(this)(session)

  def destroy()(implicit session: DBSession = Name.autoSession): Int = Name.destroy(this)(session)

}


object Name extends SQLSyntaxSupport[Name] {

  override val tableName = "NAME"

  override val columns = Seq("NAME", "EMOJI_ID")

  def apply(n: SyntaxProvider[Name])(rs: WrappedResultSet): Name = autoConstruct(rs, n)
  def apply(n: ResultName[Name])(rs: WrappedResultSet): Name = autoConstruct(rs, n)

  val n = Name.syntax("n")

  override val autoSession = AutoSession

  def find(emojiId: Int, name: String)(implicit session: DBSession = autoSession): Option[Name] = {
    withSQL {
      select.from(Name as n).where.eq(n.emojiId, emojiId).and.eq(n.name, name)
    }.map(Name(n.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Name] = {
    withSQL(select.from(Name as n)).map(Name(n.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Name as n)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Name] = {
    withSQL {
      select.from(Name as n).where.append(where)
    }.map(Name(n.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Name] = {
    withSQL {
      select.from(Name as n).where.append(where)
    }.map(Name(n.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Name as n).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: String,
    emojiId: Int)(implicit session: DBSession = autoSession): Name = {
    withSQL {
      insert.into(Name).namedValues(
        column.name -> name,
        column.emojiId -> emojiId
      )
    }.update.apply()

    Name(
      name = name,
      emojiId = emojiId)
  }

  def batchInsert(entities: Seq[Name])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'name -> entity.name,
        'emojiId -> entity.emojiId))
        SQL("""insert into NAME(
        NAME,
        EMOJI_ID
      ) values (
        {name},
        {emojiId}
      )""").batchByName(params: _*).apply[List]()
    }

  def save(entity: Name)(implicit session: DBSession = autoSession): Name = {
    withSQL {
      update(Name).set(
        column.name -> entity.name,
        column.emojiId -> entity.emojiId
      ).where.eq(column.emojiId, entity.emojiId).and.eq(column.name, entity.name)
    }.update.apply()
    entity
  }

  def destroy(entity: Name)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Name).where.eq(column.emojiId, entity.emojiId).and.eq(column.name, entity.name) }.update.apply()
  }

}
