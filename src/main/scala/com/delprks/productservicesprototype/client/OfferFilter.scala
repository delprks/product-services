package com.delprks.productservicesprototype.client

import com.delprks.productservicesprototype.domain.Status

case class OfferFilter(kind: Option[Status.Type] = None)
