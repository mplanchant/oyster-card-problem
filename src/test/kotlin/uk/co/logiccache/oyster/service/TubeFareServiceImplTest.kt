package uk.co.logiccache.oyster.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.co.logiccache.oyster.domain.BusEvent
import uk.co.logiccache.oyster.domain.CreditEvent
import uk.co.logiccache.oyster.domain.Station.*
import uk.co.logiccache.oyster.domain.TubeStartEvent
import uk.co.logiccache.oyster.service.OysterCardService.Companion.BUS_FARE
import uk.co.logiccache.oyster.service.TubeFareService.Companion.MAXIMUM_FARE
import java.math.BigDecimal

internal class TubeFareServiceImplTest {

    private val service: TubeFareService = TubeFareServiceImpl()

    @Test
    internal fun within_zone_one() {
        val eventLog = listOf(TubeStartEvent(HOLBORN, MAXIMUM_FARE))
        assertThat(service.calculateFare(eventLog, EARLS_COURT))
            .isEqualTo(BigDecimal.valueOf(2.5))
    }

    @Test
    internal fun any_one_zone_outside_zone_one() {
        val eventLog = listOf(TubeStartEvent(EARLS_COURT, MAXIMUM_FARE))
        assertThat(service.calculateFare(eventLog, HAMMERSMITH))
            .isEqualTo(BigDecimal.valueOf(2))
    }

    @Test
    internal fun any_two_zones_including_zone_one_start_in_zone_one() {
        val eventLog = listOf(TubeStartEvent(HOLBORN, MAXIMUM_FARE))
        assertThat(service.calculateFare(eventLog, HAMMERSMITH))
            .isEqualTo(BigDecimal.valueOf(3))
    }

    @Test
    internal fun any_two_zones_including_zone_one_end_in_zone_one() {
        val eventLog = listOf(TubeStartEvent(HAMMERSMITH, MAXIMUM_FARE))
        assertThat(service.calculateFare(eventLog, HOLBORN))
            .isEqualTo(BigDecimal.valueOf(3))
    }

    @Test
    internal fun any_two_zones_excluding_zone_one_start_in_zone_three() {
        val eventLog = listOf(TubeStartEvent(WIMBLEDON, MAXIMUM_FARE))
        assertThat(service.calculateFare(eventLog, HAMMERSMITH))
            .isEqualTo(BigDecimal.valueOf(2.25))
    }

    @Test
    internal fun any_two_zones_excluding_zone_one_start_in_zone_two() {
        val eventLog = listOf(TubeStartEvent(HAMMERSMITH, MAXIMUM_FARE))
        assertThat(service.calculateFare(eventLog, WIMBLEDON))
            .isEqualTo(BigDecimal.valueOf(2.25))
    }

    @Test
    internal fun any_three_zones_start_in_zone_three() {
        val eventLog = listOf(TubeStartEvent(WIMBLEDON, MAXIMUM_FARE))
        assertThat(service.calculateFare(eventLog, HOLBORN))
            .isEqualTo(BigDecimal.valueOf(3.2))
    }

    @Test
    internal fun any_three_zones_start_in_zone_one() {
        val eventLog = listOf(TubeStartEvent(HOLBORN, MAXIMUM_FARE))
        assertThat(service.calculateFare(eventLog, WIMBLEDON))
            .isEqualTo(BigDecimal.valueOf(3.2))
    }

    @Test
    internal fun start_in_zone_three_with_invalid_end() {
        val eventLog = listOf(TubeStartEvent(WIMBLEDON, MAXIMUM_FARE))
        assertThat(service.calculateFare(eventLog, CHELSEA))
            .isEqualTo(BigDecimal.valueOf(3.2))
    }

    @Test
    internal fun favour_customer_when_more_than_one_fare_possible() {
        val eventLog = listOf(TubeStartEvent(HOLBORN, MAXIMUM_FARE))
        assertThat(service.calculateFare(eventLog, EARLS_COURT))
            .isEqualTo(BigDecimal.valueOf(2.5))
    }

    @Test
    internal fun one_invalid_tube_station_charges_maximum_fare() {
        val eventLog = listOf(TubeStartEvent(EARLS_COURT, MAXIMUM_FARE))
        assertThat(service.calculateFare(eventLog, CHELSEA))
            .isEqualTo(BigDecimal.valueOf(3.2))
    }

    @Test
    internal fun two_invalid_tube_station_charges_maximum_fare() {
        val eventLog = listOf(TubeStartEvent(CHELSEA, MAXIMUM_FARE))
        assertThat(service.calculateFare(eventLog, CHELSEA))
            .isEqualTo(BigDecimal.valueOf(3.2))
    }

    @Test
    internal fun tube_end_with_no_start() {
        assertThat(service.calculateFare(listOf(), CHELSEA))
            .isEqualTo(BigDecimal.valueOf(3.2))
    }

    @Test
    internal fun previous_event_is_bus() {
        val eventLog = listOf(BusEvent(EARLS_COURT, CHELSEA, BUS_FARE))
        assertThat(service.calculateFare(eventLog, CHELSEA))
            .isEqualTo(BigDecimal.valueOf(3.2))
    }

    @Test
    internal fun previous_event_is_credit() {
        val eventLog = listOf(CreditEvent(BigDecimal.TEN))
        assertThat(service.calculateFare(eventLog, CHELSEA))
            .isEqualTo(BigDecimal.valueOf(3.2))
    }
}