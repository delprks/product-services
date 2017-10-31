package com.delprks.productservicesprototype.api.directives

trait CustomDirectives extends ResponseDirectives
  with ErrorResponseDirectives
  with PaginateDirectives
  with SortDirectives
  with FilterDirectives {

  val extractFilteringParameters = {
    availabilityStatus
  }

}
