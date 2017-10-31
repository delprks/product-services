import akka.http.scaladsl.model.ContentTypes.`application/json`
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import com.delprks.productservicesprototype.domain.Offer
import com.delprks.productservicesprototype.domain.response.{ErrorResponse, Response}
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
        response.results.last.id must be equalTo 2
      }
    }
  }

}
