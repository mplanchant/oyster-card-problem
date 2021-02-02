package uk.co.logiccache.oyster.domain

import java.math.BigDecimal

interface Event

data class BusEvent(val startLocation: Station, val endLocation: Station, val fare: BigDecimal) : Event

data class CreditEvent(val amount: BigDecimal) : Event

data class RefundEvent(val amount: BigDecimal) : Event

data class TubeEndEvent(val location: Station, val fare: BigDecimal) : Event

data class TubeStartEvent(val location: Station, val fare: BigDecimal) : Event