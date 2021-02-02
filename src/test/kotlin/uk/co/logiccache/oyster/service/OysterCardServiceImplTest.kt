package uk.co.logiccache.oyster.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.co.logiccache.oyster.domain.Customer
import uk.co.logiccache.oyster.domain.Station.*
import java.math.BigDecimal

internal class OysterCardServiceImplTest {

    private val service: OysterCardService = OysterCardServiceImpl(TubeFareServiceImpl())

    @Test
    internal fun apply_credit() {
        val fred = Customer("Fred")
        assertThat(fred.balance).isEqualTo(BigDecimal.ZERO)
        service.credit(fred, BigDecimal.valueOf(30.00))
        assertThat(fred.balance).isEqualTo(BigDecimal.valueOf(30.00))
    }

    @Test
    internal fun balance_correct_after_tube_start() {
        val fred = Customer("Fred")
        service.credit(fred, BigDecimal.valueOf(30.00))
        service.tubeStart(fred, HOLBORN)
        assertThat(fred.balance).isEqualTo(BigDecimal.valueOf(26.80))
    }

    @Test
    internal fun balance_correct_after_tube_end() {
        val fred = Customer("Fred")
        service.credit(fred, BigDecimal.valueOf(30.00))
        service.tubeStart(fred, HOLBORN)
        service.tubeEnd(fred, EARLS_COURT)
        assertThat(fred.balance).isEqualTo(BigDecimal.valueOf(27.50))
    }

    @Test
    internal fun balance_correct_after_bus_journey() {
        val fred = Customer("Fred")
        service.credit(fred, BigDecimal.valueOf(30.00))
        service.bus(fred, CHELSEA, EARLS_COURT)
        assertThat(fred.balance).isEqualTo(BigDecimal.valueOf(28.20))
    }

    @Test
    internal fun bus_journey_after_no_tube_end() {
        val fred = Customer("Fred")
        service.credit(fred, BigDecimal.valueOf(30.00))
        service.tubeStart(fred, HOLBORN)
        service.bus(fred, EARLS_COURT, CHELSEA)
        assertThat(fred.balance).isEqualTo(BigDecimal.valueOf(25.00))
    }
}