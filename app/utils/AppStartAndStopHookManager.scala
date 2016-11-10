package utils

import javax.inject.{Inject, Singleton}

import models.User
import org.slf4j.LoggerFactory

import scala.concurrent.Future

import play.api.Logger
import play.api.inject.ApplicationLifecycle

@Singleton
class AppStartAndStopHookManager @Inject()(appLifecycle: ApplicationLifecycle) {

  val logger = LoggerFactory.getLogger(getClass.getSimpleName)

  // ScalikeJDBC 1.7 requires SQLInterpolation._ import
  //import scalikejdbc._, SQLInterpolation._
  import scalikejdbc._

  // initialize JDBC driver & connection pool
  Class.forName("org.h2.Driver")
  ConnectionPool.singleton("jdbc:h2:mem:emojiban", "user", "pass")

  // ad-hoc session provider on the REPL
  implicit val session = AutoSession
//
//  // table creation, you can run DDL by using #execute as same as JDBC
//  sql"""
//    create table ALIAS (
//    ALIAS_ID serial not null primary key,
//    NAME varchar(30) not null,
//    EMOJI_ID INT not null
//    )
//  """.execute.apply()
//
//  sql"""
//    create table EMOJI (
//    EMOJI_ID serial not null primary key,
//    NAME varchar(30) not null,
//    EMOJI_ID INT not null
//    )
//  """.execute.apply()
//
//  // insert initial data
//  Map("Alice" -> "alice@hoge.com", "Bob" -> "bob@fuga.com", "Chris" -> "chris@piyo.com") foreach { case (name, email) =>
//    sql"insert into USER (NAME, EMAIL) values (${name}, ${email})".update.apply()
//  }
//
//  // for now, retrieves all data as Map value
//  val entities: List[Map[String, Any]] = sql"select * from USER".map(_.toMap).list.apply()
//
//  // defines entity object and extractor
//
//
//  // find all members
//  val members: List[User] = sql"select * from USER".map(rs => User(rs)).list.apply()
//
//  logger.debug(members.mkString(", "))

  appLifecycle.addStopHook { () =>
    //logger.info("Application Stop...")
    //logger.info("Thank you. Come again! -- Dr. Apu Nahasapeemapetilon.")
    Future.successful(())
  }

}