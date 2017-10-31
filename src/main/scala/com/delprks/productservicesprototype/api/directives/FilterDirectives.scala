package com.delprks.productservicesprototype.api.directives

import akka.http.scaladsl.server.Directive1
import akka.http.scaladsl.server.Directives._
import com.delprks.productservicesprototype.api.rejection.InvalidStatusTypeRejection
import com.delprks.productservicesprototype.domain.Status

trait FilterDirectives {
  val SupportedStatusTypes = Set("available", "pending", "expired")

  def availabilityStatus: Directive1[Seq[Status.Type]] = parameter("availabilityStatus".as[String].?).flatMap {
    case Some(statusType) =>
      isValidStatus(statusType) match {
        case true => provide {
          def toStatusType(s: String): Status.Type = s match {
            case "available" => Status.Available
            case "pending" => Status.Pending
            case "expired" => Status.Expired
          }

          statusType.split(",").map(toStatusType)
        }

        case false => reject(InvalidStatusTypeRejection())
      }

    case x => provide(List())
  }


  def isValidStatus(programmeType: String): Boolean =
    programmeType.split(",").forall(SupportedStatusTypes.contains)

}
