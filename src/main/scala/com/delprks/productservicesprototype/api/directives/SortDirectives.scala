package com.delprks.productservicesprototype.api.directives

import akka.http.scaladsl.server.Directive1
import akka.http.scaladsl.server.Directives._
import com.delprks.productservicesprototype.api.params.Sort

trait SortDirectives {
  def sort: Directive1[Sort] =
    readSortParams.map {
      case Some(sort) =>
        sort.startsWith("-") match {
          case true => Sort(Some(sort.substring(1)), isAscending = false)
          case false => Sort(Some(sort))
        }
      case None => Sort(None)
    }

  private val readSortParams = parameter("sort".as[String].?)
}
