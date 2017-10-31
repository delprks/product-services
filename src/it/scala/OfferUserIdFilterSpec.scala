import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import com.delprks.productservicesprototype.domain.Offer
import com.delprks.productservicesprototype.domain.response.Response
import util.AbstractOffersSpec

class OfferUserIdFilterSpec extends AbstractOffersSpec {

  "The service" should {

    "return offers for the provided user ID" in new OffersScope {
      insertOffer(id = 1, userId = 1)
      insertOffer(id = 2, userId = 2)
      insertOffer(id = 3, userId = 1)

      Get("/offers?user_id=1") ~> Route.seal(routes) ~> check {
        val response = responseAs[Response[Offer]]

        response.total must be equalTo 2
        response.results.length must be equalTo 2
        response.results.head.id must be equalTo 1
        response.results.last.id must be equalTo 3
      }
    }

    "return empty response if the provided user ID doesn't have any associated offers" in new OffersScope {
      Get("/offers?user_id=10") ~> Route.seal(routes) ~> check {
        val response = responseAs[Response[Offer]]

        response.total must be equalTo 0
        response.results.length must be equalTo 0
      }
    }

    "return 400 when user ID filter is invalid" in new OffersScope {
      Get("/offers?user_id=invalid") ~> Route.seal(routes) ~> check {
        status must be equalTo StatusCodes.BadRequest
      }
    }
  }

}
