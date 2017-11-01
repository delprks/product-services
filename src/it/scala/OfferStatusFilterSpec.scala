import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import com.delprks.productservicesprototype.domain.Offer
import com.delprks.productservicesprototype.domain.response.Responses
import util.AbstractOffersSpec

class OfferStatusFilterSpec extends AbstractOffersSpec {

  "The service" should {

    "return offers with available status" in new OffersScope {
      insertOffer(id = 1, status = "available")
      insertOffer(id = 2, status = "expired")
      insertOffer(id = 3, status = "available")

      Get("/offers?status=available") ~> Route.seal(routes) ~> check {
        val response = responseAs[Responses[Offer]]

        response.total must be equalTo 2
        response.results.length must be equalTo 2
        response.results.head.id must be equalTo 1
        response.results.last.id must be equalTo 3
      }
    }

    "return 400 when status filter is invalid" in new OffersScope {
      Get("/offers?status=invalid") ~> Route.seal(routes) ~> check {
        status must be equalTo StatusCodes.BadRequest
      }
    }

  }

}
