package models

import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._
import java.time.{ZonedDateTime}
import scalikejdbc.jsr310._


class EvaluationSpec extends Specification {

  "Evaluation" should {

    val e = Evaluation.syntax("e")

    "find by primary keys" in new AutoRollback {
      val maybeFound = Evaluation.find(123)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = Evaluation.findBy(sqls.eq(e.evaluationId, 123))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = Evaluation.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = Evaluation.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = Evaluation.findAllBy(sqls.eq(e.evaluationId, 123))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = Evaluation.countBy(sqls.eq(e.evaluationId, 123))
      count should be_>(0L)
    }
    "create new record" in new AutoRollback {
      val created = Evaluation.create(value = "MyString", evaluatedDatetime = null, emojiId = 123)
      created should not beNull
    }
    "save a record" in new AutoRollback {
      val entity = Evaluation.findAll().head
      // TODO modify something
      val modified = entity
      val updated = Evaluation.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = Evaluation.findAll().head
      val deleted = Evaluation.destroy(entity) == 1
      deleted should beTrue
      val shouldBeNone = Evaluation.find(123)
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollback {
      val entities = Evaluation.findAll()
      entities.foreach(e => Evaluation.destroy(e))
      val batchInserted = Evaluation.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }

}
