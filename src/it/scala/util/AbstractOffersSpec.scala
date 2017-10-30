package util

import java.sql.Timestamp

import akka.actor.ActorSystem
import akka.http.scaladsl.testkit.Specs2RouteTest
import akka.stream.ActorMaterializer
import com.delprks.productservicesprototype.api.Api
import com.delprks.productservicesprototype.config.Config
import com.delprks.productservicesprototype.domain.marshalling.JsonSerializers
import org.joda.time.DateTime
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.specification.{AfterEach, BeforeEach, Scope}
import slick.driver.H2Driver.api._

import scala.concurrent.ExecutionContextExecutor

trait AbstractOffersSpec extends Specification
  with DatabaseOperations
  with BeforeEach
  with AfterEach
  with Specs2RouteTest
  with Mockito
  with JsonSerializers {

  override val database: Database = Database.forConfig("database")

  sequential

  override def before {
    createOffersTable()
  }

  override def after {
    dropOffersTable()
  }

  trait OffersScope extends Scope
    with Api
    with Config {

    implicit val system: ActorSystem = ActorSystem("delprks-product-services-prototype")
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    implicit def executor: ExecutionContextExecutor = system.dispatcher
  }

  protected val SQLPastDate: Timestamp = {
    val dateTime = new DateTime("2016-06-17T14:20:25").getMillis
    new Timestamp(dateTime)
  }

  protected val SQLFutureDate: Timestamp = {
    val dateTime = new DateTime("2025-06-17T14:20:25").getMillis
    new Timestamp(dateTime)
  }

  protected val SQLYesterdayDate: Timestamp = {
    val dateTime = DateTime.now().minusDays(1).getMillis
    new Timestamp(dateTime)
  }

}
