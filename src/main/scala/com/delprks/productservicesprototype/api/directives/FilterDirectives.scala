package com.delprks.productservicesprototype.api.directives

import akka.http.scaladsl.server.Directive1
import akka.http.scaladsl.server.Directives._
import com.delprks.productservicesprototype.api.rejection.InvalidStatusTypeRejection
import com.delprks.productservicesprototype.domain.Status

trait FilterDirectives {
  private val SupportedStatusTypes = Set("available", "pending", "expired", "cancelled")

  def availabilityStatus: Directive1[Option[Status.Type]] = parameter("status".as[String].?).flatMap {
    case Some(statusType) =>
      if (isValidStatus(statusType)) {
        provide {
          statusType match {
            case "available" => Some(Status.Available)
            case "pending" => Some(Status.Pending)
            case "expired" => Some(Status.Expired)
            case "cancelled" => Some(Status.Cancelled)
          }
        }
      } else {
        reject(InvalidStatusTypeRejection)
      }

    case _ => provide(None)
  }

  def isValidStatus(status: String): Boolean = SupportedStatusTypes.contains(status)

  def userId: Directive1[Option[Int]] = parameter("user_id".as[Int].?).flatMap {
    case Some(userId) => provide(Some(userId))
    case _ => provide(None)
  }
}
