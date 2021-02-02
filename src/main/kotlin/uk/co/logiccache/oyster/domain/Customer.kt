package uk.co.logiccache.oyster.domain

import java.math.BigDecimal

class Customer(val name: String) {
    var balance: BigDecimal = BigDecimal.ZERO
    val eventLog = mutableListOf<Event>()
}