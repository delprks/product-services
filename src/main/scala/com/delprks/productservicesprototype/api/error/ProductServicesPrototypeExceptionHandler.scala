package com.delprks.productservicesprototype.api.error

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.ExceptionHandler
import com.delprks.productservicesprototype.api.directives.ErrorResponseDirectives
import com.delprks.productservicesprototype.api.directives.ErrorResponseDirectives.ErrorResponseData
import com.delprks.productservicesprototype.config.Config
import org.slf4s.Logging

trait ProductServicesPrototypeExceptionHandler extends ErrorResponseDirectives
  with Config
  with Logging {

  implicit val routingExceptionHandler: ExceptionHandler =
    ExceptionHandler {
      case exception => {
        log.error(exception.getMessage, exception)
        completeWithError(errorSchemaUrl, errorDocumentationUrl) {
          ErrorResponseData(StatusCodes.InternalServerError, "Server Error")
        }
      }
    }
}
