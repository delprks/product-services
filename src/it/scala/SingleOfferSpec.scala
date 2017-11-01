import akka.http.scaladsl.model.StatusCodes
import com.delprks.productservicesprototype.domain.{Offer, Status}
import com.delprks.productservicesprototype.domain.response.{Responses, SingleResponse}
import util.AbstractOffersSpec

class SingleOfferSpec extends AbstractOffersSpec {

  "The service" should {
    "return the offer if it exists in the database" in new OffersScope {
      private val offerId = 2250

      insertOffer(
        id = offerId,
        title = "iPhone X",
        availableFrom = SQLPastDate,
        availableTo = SQLFutureDate
      )

      Get(s"/offers/$offerId") ~> routes ~> check {
        val response = responseAs[SingleResponse[Offer]]

        val offer = response.result

        offer.id shouldEqual 2250
        offer.title shouldEqual "iPhone X"
        offer.availableFrom shouldEqual "2016-06-17T14:20:25Z"
        offer.availableTo shouldEqual "2025-06-17T14:20:25Z"
        offer.status shouldEqual Status.Available
      }
    }

    "return 404 if the offer doesn't exist in the database" in new OffersScope {
      private val offerId = 2250

      Get(s"/offers/$offerId") ~> routes ~> check {
        status should be equalTo StatusCodes.NotFound
      }
    }

    "show offer as unavailable if it has expired" in new OffersScope {
      private val offerId = 2250

      insertOffer(
        id = offerId,
        availableFrom = SQLPastDate,
        availableTo = SQLYesterdayDate
      )

      Get(s"/offers/$offerId") ~> routes ~> check {
        val response = responseAs[SingleResponse[Offer]]

        val offer = response.result

        offer.status shouldEqual Status.Expired
      }
    }

    "show offer as pending if it will be available in future" in new OffersScope {
      private val offerId = 2250

      insertOffer(
        id = offerId,
        availableFrom = SQLFutureDate,
        availableTo = SQLFutureDate
      )

      Get(s"/offers/$offerId") ~> routes ~> check {
        val response = responseAs[SingleResponse[Offer]]

        val offer = response.result

        offer.status shouldEqual Status.Pending
      }
    }
  }
}
