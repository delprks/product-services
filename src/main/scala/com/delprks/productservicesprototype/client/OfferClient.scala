package com.delprks.productservicesprototype.client

import java.sql.Timestamp

import com.delprks.productservicesprototype.config.Config
import com.delprks.productservicesprototype.domain.Offer
import slick.driver.PostgresDriver.api._

import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.GetResult

case class OfferQueryResult(
  id: Int,
  userId: Int,
  title: String,
  description: String,
  headline: Option[String],
  condition: String,
  availableFrom: Timestamp,
  availableTo: Timestamp,
  startingPrice: Int,
  currency: String,
  category: String
)

class OfferClient(database: Database)
  (implicit val executionContext: ExecutionContext) extends Config {

  private val EmptyQuery: String = ""

  implicit val offerQueryResult: AnyRef with GetResult[OfferQueryResult] = GetResult { result =>
    OfferQueryResult(
      id = result.nextInt(),
      userId = result.nextInt(),
      title = result.nextString(),
      description = result.nextString(),
      headline = result.nextStringOption(),
      condition = result.nextString(),
      availableFrom = result.nextTimestamp(),
      availableTo = result.nextTimestamp(),
      startingPrice = result.nextInt(),
      currency = result.nextString(),
      category = result.nextString()
    )
  }

  def offersQuery(offset: Int, limit: Int, filter: OfferFilter): DBIO[Seq[OfferQueryResult]] = {
    sql"""
       SELECT
         id,
         user_id,
         title,
         description,
         headline,
         condition,
         available_from,
         available_to,
         starting_price,
         currency,
         category
         FROM main.offer
         #${useFilters(filter)}
      """.as[OfferQueryResult]
  }

  def offerQuery(offerId: Int): DBIO[Option[OfferQueryResult]] = {
    sql"""
       SELECT
         id,
         user_id,
         title,
         description,
         headline,
         condition,
         available_from,
         available_to,
         starting_price,
         currency,
         category
         FROM main.offer
         WHERE id = $offerId
         LIMIT 1
      """.as[OfferQueryResult].headOption
  }

  def offersCountQuery(filter: OfferFilter): DBIO[Int] = {
    sql"""
       SELECT COUNT(*)
       FROM main.offer
       #${useFilters(filter)}
      """.as[Int].head
  }

  def useFilters(filter: OfferFilter): String = {
    val filters: List[String] = List(
      useStatusFilter(filter)
    )

    val filterQueries = filters.filter(_.nonEmpty)

    if (filterQueries.nonEmpty) {
      filterQueries mkString("WHERE ", " AND ", "")
    } else {
      EmptyQuery
    }
  }

  private def useStatusFilter(filter: OfferFilter) = if (filter.status.nonEmpty) {
    s"status IN ${toSqlStringSet(filter.status.map(_.toString))}"
  } else {
    EmptyQuery
  }

  def offers(offset: Int, limit: Int, filter: OfferFilter = OfferFilter()): Future[Seq[Offer]] = {
    for {
      offersQueryResult <- database run offersQuery(offset, limit, filter)
      offers = offersQueryResult map OfferMapper.mapOffer
    } yield offers
  }

  def offer(offerId: Int): Future[Option[Offer]] = {
    for {
      offerQueryResult <- database run offerQuery(offerId)
      offer = offerQueryResult map OfferMapper.mapOffer
    } yield offer
  }

  def offersCount(filter: OfferFilter = OfferFilter()): Future[Int] = database run offersCountQuery(filter)

  protected def toSqlStringSet(items: Seq[String]): String = {
    s"('${items.mkString("','")}')"
  }
}
