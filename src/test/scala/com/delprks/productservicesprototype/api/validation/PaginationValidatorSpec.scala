package com.delprks.productservicesprototype.api.validation

import org.specs2.mutable.Specification
import org.specs2.specification.Scope
import scala.util.Random

class PaginationValidatorSpec extends Specification {
  "The isInvalidPageLimit method" should {
    "return true for a page size of -1" in new PaginationValidatorScope {
      validator.isInvalidPageLimit(maximum)(-1) must beTrue
    }

    "return false for page size of 0" in new PaginationValidatorScope {
      validator.isInvalidPageLimit(maximum)(0) must beFalse
    }

    "return false page size of the maximum" in new PaginationValidatorScope {
      validator.isInvalidPageLimit(maximum)(maximum) must beFalse
    }

    "return true a page size of more than the maximum" in new PaginationValidatorScope {
      validator.isInvalidPageLimit(maximum)(maximum + 1) must beTrue
    }
  }

  "The isInvalidOffset method" should {
    "return true for offsets less than 0" in new PaginationValidatorScope {
      validator.isInvalidOffset(-1) must beTrue
    }

    "return false for an offset of 0" in new PaginationValidatorScope {
      validator.isInvalidOffset(0) must beFalse
    }

    "return false for any positive integer" in new PaginationValidatorScope {
      Seq(1, 7, 3000, Integer.MAX_VALUE).exists(validator.isInvalidOffset) must beFalse
    }
  }
}

trait PaginationValidatorScope extends Scope {
  val maximum = 9
  val validator = new PaginationValidator
}
