package com.delprks.productservicesprototype.client

import java.sql.Timestamp
import java.text.SimpleDateFormat

import com.delprks.productservicesprototype.domain.{Offer, Status}
import org.joda.time.DateTime

object OfferMapper {
  final val ISO8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'"

  def mapOffer(offerData: OfferQueryResult): Offer = {
    Offer(
      id = offerData.id,
      userId = offerData.userId,
      title = offerData.title,
      description = offerData.description,
      headline = offerData.headline,
      condition = offerData.condition,
      availableFrom = timestampToString(offerData.availableFrom),
      availableTo = timestampToString(offerData.availableTo),
      status = Status(offerData.availableFrom, offerData.availableTo),
      startingPrice = offerData.startingPrice,
      currency = offerData.currency,
      category = offerData.category
    )
  }

  // creating new instances of SimpleDateFormat to avoid http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6231579
  private def timestampToString(timestamp: Timestamp): String = new SimpleDateFormat(ISO8601).format(timestamp)

}
