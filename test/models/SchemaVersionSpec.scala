package models

import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._
import java.time.{ZonedDateTime}
import scalikejdbc.jsr310._


class SchemaVersionSpec extends Specification {

  "SchemaVersion" should {

    val sv = SchemaVersion.syntax("sv")

    "find by primary keys" in new AutoRollback {
      val maybeFound = SchemaVersion.find("MyString")
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = SchemaVersion.findBy(sqls.eq(sv.version, "MyString"))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = SchemaVersion.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = SchemaVersion.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = SchemaVersion.findAllBy(sqls.eq(sv.version, "MyString"))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = SchemaVersion.countBy(sqls.eq(sv.version, "MyString"))
      count should be_>(0L)
    }
    "create new record" in new AutoRollback {
      val created = SchemaVersion.create(versionRank = 123, installedRank = 123, version = "MyString", description = "MyString", `type` = "MyString", script = "MyString", installedBy = "MyString", installedOn = null, executionTime = 123, success = false)
      created should not beNull
    }
    "save a record" in new AutoRollback {
      val entity = SchemaVersion.findAll().head
      // TODO modify something
      val modified = entity
      val updated = SchemaVersion.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = SchemaVersion.findAll().head
      val deleted = SchemaVersion.destroy(entity) == 1
      deleted should beTrue
      val shouldBeNone = SchemaVersion.find("MyString")
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollback {
      val entities = SchemaVersion.findAll()
      entities.foreach(e => SchemaVersion.destroy(e))
      val batchInserted = SchemaVersion.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }

}
