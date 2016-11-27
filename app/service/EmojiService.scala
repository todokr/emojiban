package service

import java.io.File
import java.sql.Time
import java.time.ZonedDateTime

import scalikejdbc._
import scalaz.{-\/, \/-, \/}

import models.{Evaluation, Name, Emoji}
import service.EmojiService.DisplayEmoji
import utils.SecurityUtils

trait EmojiService {
  self: S3Service =>

  implicit val session = AutoSession
  val emojiUrlPrefix = "https://s3-ap-northeast-1.amazonaws.com/emojiban-dev/" // TODO

  val (e, ev, n) = (Emoji.syntax, Evaluation.syntax, Name.syntax)

  def popularEmoji(size: Int): Seq[DisplayEmoji] = {
    findAllEmoji.sortBy(_.evaluation).reverse.take(size)
  }

  def newEmoji(size: Int): Seq[DisplayEmoji] = {
    findAllEmoji.sortBy(_.createdDatetime).reverse.take(size)
  }

  def saveEmoji(userId: Int, file: File, names: Seq[String], contentType: Option[String]): \/[String, String] = {
    val currentTime = ZonedDateTime.now()
    val key = SecurityUtils.sha256(userId.toString + currentTime.toEpochSecond.toString)
    val entity = Emoji(
      emojiId = 0,
      imagePath = key,
      createdDatetime = currentTime,
      userId = userId
    )
    Emoji.create(entity.imagePath, entity.createdDatetime, entity.userId, names)
    uploadToS3(key, file, contentType)
  }

  private def findAllEmoji: Seq[DisplayEmoji] = {
    Emoji.findAll() map { e =>
      DisplayEmoji(
        emojiId = e.emojiId,
        imagePath = emojiUrlPrefix + e.imagePath,
        createdDatetime = e.createdDatetime.toEpochSecond,
        userId = e.userId,
        evaluation = {
          val (good, bad) = e.evaluation.partition(_.value == "G")
          good.size - bad.size
        },
        downloadCount = 0, //TODO
        names = e.name.map(_.name)
      )

    }
  }
}

object EmojiService {
  case class DisplayEmoji(
    emojiId: Int,
    imagePath: String,
    createdDatetime: Long,
    userId: Int,
    evaluation: Int,
    downloadCount: Int,
    names: Seq[String]
  )
}