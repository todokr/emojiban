package controllers

import play.api.mvc._

import models.User

class Application extends Controller {

  implicit val user = Some(User(1, "test@test.com", "テスト太郎"))

  def index = Action {
    Ok(views.html.index(user))
  }

}