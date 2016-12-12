package controllers

import play.api.mvc._

import models.{Emoji, User}
import service.{S3Service, EmojiService}
import utils.AppConfigProvider

class IndexController extends ControllerBase with EmojiService with S3Service with AppConfigProvider {

  //implicit val user = Some(User(1, "test@test.com", "テスト太郎"))

  def index = Action {
    Ok(views.html.index(None))
  }

}