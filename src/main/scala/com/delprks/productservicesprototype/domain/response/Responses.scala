package com.delprks.productservicesprototype.domain.response

case class Responses[T](
  `$schema`: String,
  total: Int,
  limit: Int,
  offset: Int,
  results: Seq[T]
)

case class SingleResponse[T](
  `$schema`: String,
  result: T
)
