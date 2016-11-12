package service

import java.time.ZonedDateTime

import models.{Evaluation, Name, Emoji}
import scalikejdbc._
import service.EmojiService.DisplayEmoji

/**
  * Created by Shunsuke on 2016/11/12.
  */
trait EmojiService {

  implicit val session = AutoSession

  val (e, ev, n) = (Emoji.syntax, Evaluation.syntax, Name.syntax)

  def popularEmoji(size: Int): Seq[DisplayEmoji] = {
    findAllEmoji.sortBy(_.evaluation).reverse.take(size)
  }

  def newEmoji(size: Int): Seq[DisplayEmoji] = {
    findAllEmoji.sortBy(_.createdDatetime).reverse.take(size)
  }

  private def findAllEmoji: Seq[DisplayEmoji] = {
    Emoji.findAll() map { e =>
      DisplayEmoji(
        emojiId = e.emojiId,
        imagePath = e.imagePath,
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