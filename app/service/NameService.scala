package service

import models.Name

import scala.collection.immutable.ListMap

/**
  * Created by Shunsuke on 2016/11/11.
  */
trait NameService {

  def listNames: Map[String, Seq[Name]] = {
    val names = Name.findAll().groupBy(_.name).toSeq.sortBy(_._1)
    ListMap(names:_*)
  }
}
