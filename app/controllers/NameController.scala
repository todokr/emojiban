package controllers

import models.Name
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import service.NameService

/**
  * Created by Shunsuke on 2016/11/11.
  */
class NameController extends Controller with NameService {

  implicit val aliasFormat = Json.format[Name]

  def index = Action {
    Ok(Json.toJson(listNames)).as(JSON)
  }
}
