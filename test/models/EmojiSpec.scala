package models

import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._
import java.time.{ZonedDateTime}
import scalikejdbc.jsr310._


class EmojiSpec extends Specification {

  "Emoji" should {

    val e = Emoji.syntax("e")

    "find by primary keys" in new AutoRollback {
      val maybeFound = Emoji.find(123)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = Emoji.findBy(sqls.eq(e.emojiId, 123))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = Emoji.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = Emoji.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = Emoji.findAllBy(sqls.eq(e.emojiId, 123))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = Emoji.countBy(sqls.eq(e.emojiId, 123))
      count should be_>(0L)
    }
    "create new record" in new AutoRollback {
      val created = Emoji.create(imagePath = "MyString", createdDatetime = null, userId = 123)
      created should not beNull
    }
    "save a record" in new AutoRollback {
      val entity = Emoji.findAll().head
      // TODO modify something
      val modified = entity
      val updated = Emoji.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = Emoji.findAll().head
      val deleted = Emoji.destroy(entity) == 1
      deleted should beTrue
      val shouldBeNone = Emoji.find(123)
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollback {
      val entities = Emoji.findAll()
      entities.foreach(e => Emoji.destroy(e))
      val batchInserted = Emoji.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }

}
