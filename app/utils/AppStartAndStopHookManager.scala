package utils

import javax.inject.{Inject, Singleton}

import org.slf4j.LoggerFactory
import scalikejdbc.config.DBs

import scala.concurrent.Future

import play.api.inject.ApplicationLifecycle
import scalikejdbc._

@Singleton
class AppStartAndStopHookManager @Inject()(
  appLifecycle: ApplicationLifecycle
) {

  val logger = LoggerFactory.getLogger(getClass.getSimpleName)

  DBs.setupAll()
  implicit val session = AutoSession

  appLifecycle.addStopHook { () =>
    logger.info("Application Stop...")
    logger.info("Thank you. Come again! -- Dr. Apu Nahasapeemapetilon.")
    Future.successful(())
  }

}