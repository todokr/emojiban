package controllers

import play.api.libs.json.Json
import play.api.mvc.Action
import scalaz.{-\/, \/-, \/}

import service.{S3Service, EmojiService}
import service.EmojiService.DisplayEmoji

/**
  * Created by Shunsuke on 2016/11/13.
  */
class EmojiController extends ControllerBase with S3Service with EmojiService {

  implicit val emojiFormat = Json.format[DisplayEmoji]

  def popularEmojis = Action { implicit r =>
    Ok(Json.toJson(popularEmoji(10)))
  }

  def newEmojis = Action { implicit r =>
    Ok(Json.toJson(newEmoji(10)))
  }

  def save = Action(parse.multipartFormData) { implicit r =>
    r.body.file("emoji").map { emoji =>
      val userId = 1 // TODO
      saveEmoji(userId, emoji.ref.file) match {
        case \/-(x) => Ok(Json.obj("emojiPath" -> x))
        case -\/(err) => BadRequest(Json.obj("message" -> err))
      }
    } getOrElse BadRequest(Json.obj("message" -> "File is none"))
  }

}
