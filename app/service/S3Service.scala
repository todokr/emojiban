package service

import java.nio.file.{Paths, Files}

import com.amazonaws.services.s3.model.ObjectMetadata

import scala.util.control.Exception._

import awscala._, s3._
import scalaz.{-\/, \/-, \/}

trait S3Service {

  implicit val s3 = S3()(Region.Tokyo)

  def selectUrl(emojiId: Int): Option[String] = ???

  val bucket = s3.bucket("emojiban-dev")

  def uploadToS3(key: String, file: java.io.File, contentType: Option[String]): \/[String, String] = {

    val imageMimes = Seq("image/jpeg", "image/png", "image/gif")
    val imageContentType = contentType.find(m => imageMimes contains m)
    val fileAsBytes = Files.readAllBytes(file.toPath)

    allCatch opt {
      bucket foreach { b =>
        imageContentType foreach { m =>
          val metaData = new ObjectMetadata()
          metaData.setContentType(m)
          b.putObjectAsPublicRead(key, fileAsBytes, metaData)
        }
      }
    } match {
      case Some(_) => \/-(key)
      case None => -\/("Failed to upload to S3")
    }
  }

}
