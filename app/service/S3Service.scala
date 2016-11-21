package service

import awscala._, s3._
import scalaz.{-\/, \/-, \/}

trait S3Service {

  implicit val s3 = S3()(Region.Tokyo)

  def selectUrl(emojiId: Int): Option[String] = ???

  val bucket = s3.bucket("emojiban-dev")

  def uploadToS3(key: String, file: java.io.File): \/[String, String] = {
    bucket.map(_.put(key, file)) match {
      case Some(x) => \/-(x.key)
      case None    => -\/("Failed to upload to S3")
    }
  }



}
