package com.delprks.productservicesprototype.api.directives

trait CustomDirectives extends ResponseDirectives
  with ErrorResponseDirectives
  with PaginateDirectives
  with FilterDirectives
  with JsonDirectives
  with OfferDirectives {

  val extractFilteringParameters = {
    availabilityStatus &
    userId
  }

}
