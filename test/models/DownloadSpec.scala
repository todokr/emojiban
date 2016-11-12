package models

import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._
import java.time.{ZonedDateTime}
import scalikejdbc.jsr310._


class DownloadSpec extends Specification {

  "Download" should {

    val d = Download.syntax("d")

    "find by primary keys" in new AutoRollback {
      val maybeFound = Download.find(123)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = Download.findBy(sqls.eq(d.downloadId, 123))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = Download.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = Download.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = Download.findAllBy(sqls.eq(d.downloadId, 123))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = Download.countBy(sqls.eq(d.downloadId, 123))
      count should be_>(0L)
    }
    "create new record" in new AutoRollback {
      val created = Download.create(downloadDatetime = null, emojiId = 123)
      created should not beNull
    }
    "save a record" in new AutoRollback {
      val entity = Download.findAll().head
      // TODO modify something
      val modified = entity
      val updated = Download.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = Download.findAll().head
      val deleted = Download.destroy(entity) == 1
      deleted should beTrue
      val shouldBeNone = Download.find(123)
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollback {
      val entities = Download.findAll()
      entities.foreach(e => Download.destroy(e))
      val batchInserted = Download.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }

}
