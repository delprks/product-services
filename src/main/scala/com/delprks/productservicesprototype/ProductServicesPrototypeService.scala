package com.delprks.productservicesprototype

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import com.delprks.productservicesprototype.api.Api
import com.delprks.productservicesprototype.config.Config
import org.slf4s.Logging
import scala.concurrent.ExecutionContextExecutor

object ProductServicesPrototypeService extends App
  with Api
  with Config
  with Logging {

  implicit val system: ActorSystem = ProductServicesPrototypeContext.system
  implicit val materializer: ActorMaterializer = ProductServicesPrototypeContext.materializer

  implicit def executor: ExecutionContextExecutor = system.dispatcher

  Http().bindAndHandle(routes, httpInterface, httpPort)

  log.info(s"Starting $serviceName on $httpInterface:$httpPort")
}
