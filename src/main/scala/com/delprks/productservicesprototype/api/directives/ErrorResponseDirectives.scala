package com.delprks.productservicesprototype.api.directives

import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.StandardRoute
import com.delprks.productservicesprototype.api.directives.ErrorResponseDirectives.ErrorResponseData
import com.delprks.productservicesprototype.domain.marshalling.JsonSerializers
import com.delprks.productservicesprototype.domain.response.ErrorResponse

trait ErrorResponseDirectives extends JsonSerializers {

  def completeWithError(schemaUrl: String, documentationUrl: String)
                       (errorResponseData: ErrorResponseData): StandardRoute = {

    val errorResponse = ErrorResponse(
      jsonSchemaUrl = schemaUrl,
      documentationUrl = documentationUrl,
      httpStatus = errorResponseData.statusCode.intValue,
      message = errorResponseData.message)

    complete(errorResponseData.statusCode, errorResponse)
  }

}

object ErrorResponseDirectives {
  case class ErrorResponseData(statusCode: StatusCode, message: String)
}
