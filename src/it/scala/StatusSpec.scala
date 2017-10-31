import akka.http.scaladsl.model.ContentTypes.`application/json`
import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.testkit.Specs2RouteTest
import com.delprks.productservicesprototype.api.Api
import com.delprks.productservicesprototype.domain.response.ApiStatus
import org.specs2.mutable.Specification

class StatusSpec extends Specification
  with Specs2RouteTest
  with Api {

  "The service" should {
    "respond with a 200 at /status" in {
      Get("/status") ~> routes ~> check {
        status must be equalTo OK
        contentType must be equalTo `application/json`
        responseAs[ApiStatus] must be equalTo ApiStatus("OK")
      }
    }
  }
}
