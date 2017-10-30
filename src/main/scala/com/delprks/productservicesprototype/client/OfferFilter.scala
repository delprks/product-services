package com.delprks.productservicesprototype.client

import com.delprks.productservicesprototype.domain.Kind

case class OfferFilter(kind: Option[Kind.Type] = None)
