import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import com.delprks.productservicesprototype.domain.Offer
import com.delprks.productservicesprototype.domain.response.Response
import util.AbstractOffersSpec

class StatusFilterSpec extends AbstractOffersSpec {

  "The service" should {

    "return offers with available status" in new OffersScope {
      insertOffer(id = 1, status = "available")
      insertOffer(id = 2, status = "expired")
      insertOffer(id = 3, status = "available")

      Get("/offers?status=available") ~> Route.seal(routes) ~> check {
        val response = responseAs[Response[Offer]]

        response.total must be equalTo 2
        response.results.length must be equalTo 2
        response.results.head.id must be equalTo 1
        response.results.last.id must be equalTo 3
      }
    }

    "return offers with available and pending statuses" in new OffersScope {
      insertOffer(id = 1, status = "available")
      insertOffer(id = 2, status = "expired")
      insertOffer(id = 3, status = "available")
      insertOffer(id = 4, status = "pending")

      Get("/offers?status=available,pending") ~> Route.seal(routes) ~> check {
        val response = responseAs[Response[Offer]]

        response.total must be equalTo 3
        response.results.length must be equalTo 3
        response.results.head.id must be equalTo 1
        response.results(1).id must be equalTo 3
        response.results.last.id must be equalTo 4
      }
    }

    "return 400 when status filter is invalid" in new OffersScope {
      Get("/offers?status=invalid") ~> Route.seal(routes) ~> check {
        status must be equalTo StatusCodes.BadRequest
      }
    }

    "return 400 when any of the status filters are invalid" in new OffersScope {
      Get("/offers?status=available,invalid") ~> Route.seal(routes) ~> check {
        status must be equalTo StatusCodes.BadRequest
      }
    }
  }

}
