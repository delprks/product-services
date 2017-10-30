package com.delprks.productservicesprototype.domain

case class Offer(
  id: Int,
  userId: Int,
  title: String,
  description: String,
  headline: Option[String],
  condition: String,
  availableFrom: String,
  availableTo: String,
  available: Boolean,
  startingPrice: Int,
  currency: String,
  category: String
)
