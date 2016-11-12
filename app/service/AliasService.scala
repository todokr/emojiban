package service

import service.AliasService.Alias

import scala.collection.immutable.ListMap

/**
  * Created by Shunsuke on 2016/11/11.
  */
trait AliasService {

  def listAlias: Map[String, Seq[Alias]] = {
    sql""
//    val s = Seq(
//      Alias(1, "swimmy", 1),
//      Alias(2, "minami", 1),
//      Alias(3, "souichirou", 1),
//      Alias(4, "haisho", 2),
//      Alias(5, "hitachi", 2),
//      Alias(6, "avocado", 3),
//      Alias(7, "kougan", 3),
//      Alias(8, "zetsubou", 4),
//      Alias(9, "azs", 5),
//      Alias(10, "minami", 6),
//      Alias(10, "minami", 7)
//    ).groupBy(_.name).toSeq.sortBy(_._1)
//    ListMap(s:_*)
  }
}

object AliasService {

  case class Alias(
    aliasId: Int,
    name: String,
    emojiId: Int
  )
}
