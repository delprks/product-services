import akka.http.scaladsl.model._
import com.delprks.productservicesprototype.domain.response.Response
import com.delprks.productservicesprototype.domain.{Offer, Status}
import util.AbstractOffersSpec

class CreateOfferSpec extends AbstractOffersSpec {

  "The service" should {
    "return the offer if it exists in the database" in new OffersScope {
      val body: String =
        """
          |{
          |	"userId": 300922,
          |	"title": "Apple iPhone 8 - 64GB - Gold",
          |	"description": "The Apple iPhone offers a host of user-friendly features and a smooth interface.",
          |	"headline": "With box",
          |	"condition": "used",
          |	"availableFrom": "2020-11-21T14:00:00Z",
          |	"availableTo": "2020-12-07T11:45:00Z",
          |	"startingPrice": 100,
          |	"currency": "pounds",
          |	"category": "smart phones"
          |}
        """.stripMargin

      val request: HttpRequest = Post("/offers")
        .withEntity(HttpEntity(ContentType(MediaTypes.`application/json`), body))

      request ~> routes ~> check {
        status must beEqualTo (StatusCodes.Accepted)
      }

      Get(s"/offers") ~> routes ~> check {
        val response = responseAs[Response[Offer]]

        val offer = response.results.head

        offer.title shouldEqual "Apple iPhone 8 - 64GB - Gold"
        offer.availableFrom shouldEqual "2020-11-21T14:00:00Z"
        offer.availableTo shouldEqual "2020-12-07T11:45:00Z"
        offer.status shouldEqual Status.Pending
      }
    }
  }
}
