package models

import scalikejdbc._
import java.time.{ZonedDateTime}
import scalikejdbc.jsr310._

case class User(
  userId: Int,
  email: String,
  name: String,
  createdDatetime: ZonedDateTime) {

  def save()(implicit session: DBSession = User.autoSession): User = User.save(this)(session)

  def destroy()(implicit session: DBSession = User.autoSession): Int = User.destroy(this)(session)

}


object User extends SQLSyntaxSupport[User] {

  override val tableName = "USER"

  override val columns = Seq("USER_ID", "EMAIL", "NAME", "CREATED_DATETIME")

  def apply(u: SyntaxProvider[User])(rs: WrappedResultSet): User = autoConstruct(rs, u)
  def apply(u: ResultName[User])(rs: WrappedResultSet): User = autoConstruct(rs, u)

  val u = User.syntax("u")

  override val autoSession = AutoSession

  def find(userId: Int)(implicit session: DBSession = autoSession): Option[User] = {
    withSQL {
      select.from(User as u).where.eq(u.userId, userId)
    }.map(User(u.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[User] = {
    withSQL(select.from(User as u)).map(User(u.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(User as u)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[User] = {
    withSQL {
      select.from(User as u).where.append(where)
    }.map(User(u.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[User] = {
    withSQL {
      select.from(User as u).where.append(where)
    }.map(User(u.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(User as u).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    userId: Int,
    email: String,
    name: String,
    createdDatetime: ZonedDateTime)(implicit session: DBSession = autoSession): User = {
    withSQL {
      insert.into(User).namedValues(
        column.userId -> userId,
        column.email -> email,
        column.name -> name,
        column.createdDatetime -> createdDatetime
      )
    }.update.apply()

    User(
      userId = userId,
      email = email,
      name = name,
      createdDatetime = createdDatetime)
  }

  def batchInsert(entities: Seq[User])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'userId -> entity.userId,
        'email -> entity.email,
        'name -> entity.name,
        'createdDatetime -> entity.createdDatetime))
        SQL("""insert into USER(
        USER_ID,
        EMAIL,
        NAME,
        CREATED_DATETIME
      ) values (
        {userId},
        {email},
        {name},
        {createdDatetime}
      )""").batchByName(params: _*).apply[List]()
    }

  def save(entity: User)(implicit session: DBSession = autoSession): User = {
    withSQL {
      update(User).set(
        column.userId -> entity.userId,
        column.email -> entity.email,
        column.name -> entity.name,
        column.createdDatetime -> entity.createdDatetime
      ).where.eq(column.userId, entity.userId)
    }.update.apply()
    entity
  }

  def destroy(entity: User)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(User).where.eq(column.userId, entity.userId) }.update.apply()
  }

}
