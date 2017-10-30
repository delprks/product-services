package com.delprks.productservicesprototype.api.rejection

import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.Rejection

sealed trait ProductServicesPrototypeRejection extends Rejection {
  def statusCode: StatusCode
  def message: String
}

case class InvalidOffsetRejection(offset: Int) extends ProductServicesPrototypeRejection {
  val statusCode = StatusCodes.BadRequest
  val message = s"Invalid value for offset: '$offset'"
}

case class InvalidPageLimitRejection(maximumPageLimit: Int) extends ProductServicesPrototypeRejection {
  val statusCode = StatusCodes.BadRequest
  val message = s"Page limit must be between 0 and $maximumPageLimit"
}

case class InvalidStatusTypeRejection() extends ProductServicesPrototypeRejection {
  val statusCode = StatusCodes.BadRequest
  val message = s"Invalid value for status. Values supported: available, pending, and expired, or comma-separated combinations"
}
