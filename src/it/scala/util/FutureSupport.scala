package util

import java.util.concurrent.TimeUnit

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

trait FutureSupport {

  protected val futureSupportTimeout: Duration = Duration(1, TimeUnit.SECONDS)

  def whenReady[Result, TransformedResult](resultFuture: Future[Result])
    (block: Result => TransformedResult): TransformedResult = {
    block {
      Await.result(resultFuture, futureSupportTimeout)
    }
  }

}
