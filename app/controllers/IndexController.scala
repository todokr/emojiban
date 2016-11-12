package controllers

import play.api.mvc._

import models.{Emoji, User}
import service.EmojiService

class IndexController extends ControllerBase with EmojiService {

  //implicit val user = Some(User(1, "test@test.com", "テスト太郎"))

  def index = Action {
    Ok(views.html.index(None))
  }

}