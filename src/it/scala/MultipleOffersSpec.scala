import akka.http.scaladsl.model.ContentTypes.`application/json`
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import com.delprks.productservicesprototype.domain.Offer
import com.delprks.productservicesprototype.domain.response.{ErrorResponse, Response}
import util.AbstractOffersSpec

class MultipleOffersSpec extends AbstractOffersSpec {

  "The service" should {

    "return a response with offers" in new OffersScope {
      insertOffer()

      Get("/offers") ~> Route.seal(routes) ~> check {
        status must be equalTo StatusCodes.OK
        contentType must be equalTo `application/json`
        responseAs[Response[Offer]].total must be > 0
      }
    }

    "return a response with the requested limit" in new OffersScope {
      insertOffer()

      Get("/offers?limit=1") ~> Route.seal(routes) ~> check {
        status must be equalTo StatusCodes.OK
        responseAs[Response[Offer]].total must be equalTo 1
      }
    }

    "return a 400 Bad Request when a limit greater than the maximum is requested" in new OffersScope {
      Get("/offers?limit=101") ~> Route.seal(routes) ~> check {
        status must be equalTo StatusCodes.BadRequest
        responseAs[ErrorResponse].errors.size must be > 0
      }
    }
  }

}
