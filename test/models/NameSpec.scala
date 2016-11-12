package models

import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._


class NameSpec extends Specification {

  "Name" should {

    val n = Name.syntax("n")

    "find by primary keys" in new AutoRollback {
      val maybeFound = Name.find(123, "MyString")
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = Name.findBy(sqls.eq(n.emojiId, 123))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = Name.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = Name.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = Name.findAllBy(sqls.eq(n.emojiId, 123))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = Name.countBy(sqls.eq(n.emojiId, 123))
      count should be_>(0L)
    }
    "create new record" in new AutoRollback {
      val created = Name.create(name = "MyString", emojiId = 123)
      created should not beNull
    }
    "save a record" in new AutoRollback {
      val entity = Name.findAll().head
      // TODO modify something
      val modified = entity
      val updated = Name.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = Name.findAll().head
      val deleted = Name.destroy(entity) == 1
      deleted should beTrue
      val shouldBeNone = Name.find(123, "MyString")
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollback {
      val entities = Name.findAll()
      entities.foreach(e => Name.destroy(e))
      val batchInserted = Name.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }

}
