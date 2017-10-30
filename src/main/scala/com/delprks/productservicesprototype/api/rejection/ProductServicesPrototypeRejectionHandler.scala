package com.delprks.productservicesprototype.api.rejection

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.RejectionHandler
import com.delprks.productservicesprototype.api.directives.ErrorResponseDirectives.ErrorResponseData
import com.delprks.productservicesprototype.api.directives.ErrorResponseDirectives
import com.delprks.productservicesprototype.config.Config

trait ProductServicesPrototypeRejectionHandler extends ErrorResponseDirectives
  with Config {

  implicit val rejectionHandler = RejectionHandler.newBuilder()
    .handle { case productServicesPrototypeRejection: ProductServicesPrototypeRejection =>
      completeWithError(errorSchemaUrl, errorDocumentationUrl) {
        ErrorResponseData(productServicesPrototypeRejection.statusCode, productServicesPrototypeRejection.message)
      }
    }
    .handleNotFound {
      completeWithError(errorSchemaUrl, errorDocumentationUrl) {
        ErrorResponseData(StatusCodes.NotFound, "Not found")
      }
    }
    .result()
}
