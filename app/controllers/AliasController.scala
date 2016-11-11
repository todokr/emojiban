package controllers

import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import service.AliasService
import service.AliasService.Alias

/**
  * Created by Shunsuke on 2016/11/11.
  */
class AliasController extends Controller with AliasService {

  implicit val aliasFormat = Json.format[Alias]

  def index = Action {
    Ok(Json.toJson(listAlias)).as(JSON)
  }
}
