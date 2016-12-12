package controllers

import play.api.libs.json.Json
import play.api.mvc.Action
import utils.AppConfigProvider
import scalaz.{-\/, \/-, \/}

import service.{S3Service, EmojiService}
import service.EmojiService.DisplayEmoji

/**
  * Created by Shunsuke on 2016/11/13.
  */
class EmojiController extends ControllerBase with S3Service with EmojiService with AppConfigProvider {

  implicit val emojiFormat = Json.format[DisplayEmoji]

  def popularEmojis = Action { implicit r =>
    Ok(Json.toJson(popularEmoji(10)))
  }

  def newEmojis = Action { implicit r =>
    Ok(Json.toJson(newEmoji(10)))
  }

  def save = Action(parse.multipartFormData) { implicit r =>
    (for {
      file <- r.body.file("emojiFile")
      name <- r.body.dataParts.get("emojiName")
    } yield {
      println(s"file: ${file.filename}")
      println(s"name: $name")
      val userId = 1 // TODO
      saveEmoji(userId, file.ref.file, name, file.contentType) match {
        case \/-(x) => Ok(Json.obj("emojiPath" -> x))
        case -\/(err) => BadRequest(Json.obj("message" -> err))
      }
    }) getOrElse BadRequest(Json.obj("message" -> "File is none"))
  }

}
