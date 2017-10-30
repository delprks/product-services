import com.delprks.productservicesprototype.domain.Offer
import com.delprks.productservicesprototype.domain.response.Response
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
        val response = responseAs[Response[Offer]]

        val offer = response.results.head

        offer.id shouldEqual 2250
        offer.title shouldEqual "iPhone X"
        offer.availableFrom shouldEqual "2016-06-17T14:20:25Z"
        offer.availableTo shouldEqual "2025-06-17T14:20:25Z"
        offer.available shouldEqual true
      }
    }

    "show offer as unavailable if it has expired" in new OffersScope {
      private val offerId = 2250

      insertOffer(
        id = offerId,
        title = "iPhone X",
        availableFrom = SQLPastDate,
        availableTo = SQLYesterdayDate
      )

      Get(s"/offers/$offerId") ~> routes ~> check {
        val response = responseAs[Response[Offer]]

        val offer = response.results.head

        offer.available shouldEqual false
      }
    }
  }
}
