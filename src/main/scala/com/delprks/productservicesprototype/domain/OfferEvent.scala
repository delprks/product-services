package com.delprks.productservicesprototype.domain

case class OfferEvent(
  userId: Int,
  title: String,
  description: String,
  headline: Option[String],
  condition: String,
  availableFrom: String,
  availableTo: String,
  startingPrice: Int,
  currency: String,
  category: String
)

case class OfferStatusEvent(
  status: String
)
