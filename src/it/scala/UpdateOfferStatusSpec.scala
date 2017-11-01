import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Route
import com.delprks.productservicesprototype.domain.response.Response
import com.delprks.productservicesprototype.domain.{Offer, Status}
import util.AbstractOffersSpec

class UpdateOfferStatusSpec extends AbstractOffersSpec {

  "The service" should {
    "set the status to cancelled if the order exists" in new OffersScope {
      private val offerId = 2250

      insertOffer(
        id = offerId,
        availableFrom = SQLPastDate,
        availableTo = SQLYesterdayDate
      )

      Get(s"/offers/$offerId") ~> routes ~> check {
        val response = responseAs[Response[Offer]]

        response.results.head.status shouldEqual Status.Expired
      }

      val body: String =
        """
          |{
          |	"status": "cancelled"
          |}
        """.stripMargin

      val request: HttpRequest = Put(s"/offers/$offerId/status")
        .withEntity(HttpEntity(ContentType(MediaTypes.`application/json`), body))

      request ~> routes ~> check {
        status must beEqualTo(StatusCodes.Accepted)
      }

      Get(s"/offers?status=cancelled") ~> routes ~> check {
        val response = responseAs[Response[Offer]]

        response.results.head.status shouldEqual Status.Cancelled
      }

    }

    "set the status to available if the order exists" in new OffersScope {
      private val offerId = 2250

      insertOffer(
        id = offerId,
        availableFrom = SQLPastDate,
        availableTo = SQLYesterdayDate
      )

      Get(s"/offers/$offerId") ~> routes ~> check {
        val response = responseAs[Response[Offer]]

        response.results.head.status shouldEqual Status.Expired
      }

      val body: String =
        """
          |{
          |	"status": "available"
          |}
        """.stripMargin

      val request: HttpRequest = Put(s"/offers/$offerId/status")
        .withEntity(HttpEntity(ContentType(MediaTypes.`application/json`), body))

      request ~> routes ~> check {
        status must beEqualTo(StatusCodes.Accepted)
      }

      Get(s"/offers") ~> routes ~> check {
        val response = responseAs[Response[Offer]]

        response.results.head.status shouldEqual Status.Expired
      }

    }

    "return 400 if supplied status is not cancelled or available" in new OffersScope {
      private val offerId = 2250

      insertOffer(
        id = offerId,
        availableFrom = SQLPastDate,
        availableTo = SQLYesterdayDate
      )

      Get(s"/offers/$offerId") ~> routes ~> check {
        val response = responseAs[Response[Offer]]

        response.results.head.status shouldEqual Status.Expired
      }

      val body: String =
        """
          |{
          |	"status": "pending"
          |}
        """.stripMargin

      val request: HttpRequest = Put(s"/offers/$offerId/status")
        .withEntity(HttpEntity(ContentType(MediaTypes.`application/json`), body))

      request ~> Route.seal(routes) ~> check {
        status must beEqualTo(StatusCodes.BadRequest)
      }

      Get(s"/offers") ~> routes ~> check {
        val response = responseAs[Response[Offer]]

        response.results.head.status shouldEqual Status.Expired
      }

    }
  }
}
