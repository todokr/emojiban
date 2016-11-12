package controllers

import play.api.libs.json.Json
import play.api.mvc.Action
import service.EmojiService
import service.EmojiService.DisplayEmoji

/**
  * Created by Shunsuke on 2016/11/13.
  */
class EmojiController extends ControllerBase with EmojiService {

  implicit val emojiFormat = Json.format[DisplayEmoji]

  def popularEmojis = Action { implicit r =>
    Ok(Json.toJson(popularEmoji(10)))
  }

  def newEmojis = Action { implicit r =>
    Ok(Json.toJson(newEmoji(10)))
  }

}
