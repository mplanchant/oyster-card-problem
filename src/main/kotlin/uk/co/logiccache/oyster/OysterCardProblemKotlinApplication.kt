package uk.co.logiccache.oyster

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import uk.co.logiccache.oyster.domain.Customer
import uk.co.logiccache.oyster.domain.Station.*
import uk.co.logiccache.oyster.service.OysterCardService
import java.math.BigDecimal
import java.math.RoundingMode

private val logger = KotlinLogging.logger {}

@SpringBootApplication
class OysterCardProblemKotlinApplication @Autowired constructor(private val oysterCardService: OysterCardService) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val bob = Customer("Bob")
        oysterCardService.credit(bob, BigDecimal.valueOf(30))
        oysterCardService.tubeStart(bob, HOLBORN)
        oysterCardService.tubeEnd(bob, EARLS_COURT)
        oysterCardService.bus(bob, EARLS_COURT, CHELSEA)
        oysterCardService.tubeStart(bob, EARLS_COURT)
        oysterCardService.tubeEnd(bob, HAMMERSMITH)
        bob.eventLog.forEach { event -> logger.info { "${bob.name}: $event" } }
        logger.info { "${bob.name}: Balance £${bob.balance.setScale(2, RoundingMode.HALF_UP)}" }
    }
}

fun main(args: Array<String>) {
    runApplication<OysterCardProblemKotlinApplication>(*args)
}
