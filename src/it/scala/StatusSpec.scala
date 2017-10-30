import akka.http.scaladsl.model.ContentTypes.`application/json`
import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.testkit.Specs2RouteTest
import com.delprks.productservicesprototype.api.Api
import com.delprks.productservicesprototype.domain.response.Status
import org.specs2.mutable.Specification

class StatusSpec extends Specification
  with Specs2RouteTest
  with Api {

  "The service" should {
    "respond with a 200 at /status" in {
      Get("/status") ~> routes ~> check {
        status must be equalTo OK
        contentType must be equalTo `application/json`
        responseAs[Status] must be equalTo Status("OK")
        header("Cache-Control").get.value must be equalTo "no-cache, no-store, must-revalidate"
      }
    }
  }
}
