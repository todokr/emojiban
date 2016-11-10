package models

import java.time.LocalDateTime

import scalikejdbc._
import scalikejdbc.WrappedResultSet

case class User(
  userId          : Int,
  email           : String,
  name            : String
)
object User extends SQLSyntaxSupport[User] {
  override val tableName = "USERS"
  def apply(rs: WrappedResultSet) = new User(
    rs.get[Int]("USER_ID"), rs.get[String]("EMAIL"), rs.get[String]("NAME"))
}

case class Emoji(
  emojiId         : Int,
  imagePath       : String,
  createdDatetime : Long,
  userId          : Int
)

case class Evaluation(
  evaluationId      :Int,
  value             : Char,
  evaluatedDatetime : LocalDateTime,
  emojiId           : Int
)