package com.delprks.productservicesprototype.api.directives

import com.delprks.productservicesprototype.api.directives.ResponseDirectives.MultiEntityResponseData
import com.delprks.productservicesprototype.api.params.Paginate
import com.delprks.productservicesprototype.domain.response.{Responses, SingleResponse}

import scala.concurrent.{ExecutionContextExecutor, Future}

trait ResponseDirectives {
  implicit def executor: ExecutionContextExecutor

  def toResponse[T](schema: String, pagination: Paginate)
    (resultsFuture: Future[MultiEntityResponseData[T]]): Future[Responses[T]] = {

    resultsFuture.map { results =>
      Responses(
        `$schema` = schema,
        total = results.total,
        limit = pagination.limit,
        offset = pagination.offset,
        results = results.entities
      )
    }
  }

  def toResponse[T](schema: String)(result: T): SingleResponse[T] = {
    SingleResponse(
      `$schema` = schema,
      result = result
    )
  }
}

object ResponseDirectives {

  case class MultiEntityResponseData[T](entities: Seq[T], total: Int)

}
