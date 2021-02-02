package uk.co.logiccache.oyster.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import uk.co.logiccache.oyster.domain.*
import uk.co.logiccache.oyster.service.OysterCardService.Companion.BUS_FARE
import uk.co.logiccache.oyster.service.TubeFareService.Companion.MAXIMUM_FARE
import java.math.BigDecimal

interface OysterCardService {
    fun tubeStart(customer: Customer, location: Station)
    fun tubeEnd(customer: Customer, location: Station)
    fun bus(customer: Customer, startLocation: Station, endLocation: Station)
    fun credit(customer: Customer, amount: BigDecimal)

    companion object {
        val BUS_FARE: BigDecimal = BigDecimal.valueOf(1.80)
    }
}

@Service
class OysterCardServiceImpl @Autowired constructor(private val tubeFareService: TubeFareService) : OysterCardService {

    override fun tubeStart(customer: Customer, location: Station) {
        customer.balance = customer.balance.subtract(MAXIMUM_FARE)
        customer.eventLog.add(TubeStartEvent(location, MAXIMUM_FARE))
    }

    override fun tubeEnd(customer: Customer, location: Station) {
        val fare = tubeFareService.calculateFare(customer.eventLog, location)
        customer.balance = customer.balance.subtract(fare)
        customer.eventLog.add(TubeEndEvent(location, fare))
        customer.balance = customer.balance.add(MAXIMUM_FARE)
        customer.eventLog.add(RefundEvent(MAXIMUM_FARE))
    }

    override fun bus(customer: Customer, startLocation: Station, endLocation: Station) {
        customer.balance = customer.balance.subtract(BUS_FARE)
        customer.eventLog.add(BusEvent(startLocation, endLocation, BUS_FARE))
    }

    override fun credit(customer: Customer, amount: BigDecimal) {
        customer.balance = customer.balance.add(amount)
        customer.eventLog.add(CreditEvent(amount))
    }
}