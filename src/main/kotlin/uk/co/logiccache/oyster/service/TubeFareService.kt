package uk.co.logiccache.oyster.service

import org.springframework.stereotype.Service
import uk.co.logiccache.oyster.domain.Event
import uk.co.logiccache.oyster.domain.Station
import uk.co.logiccache.oyster.domain.TubeStartEvent
import uk.co.logiccache.oyster.service.TubeFareService.Companion.MAXIMUM_FARE
import java.math.BigDecimal

interface TubeFareService {
    fun calculateFare(eventLog: List<Event>, endLocation: Station): BigDecimal

    companion object {
        val MAXIMUM_FARE: BigDecimal = BigDecimal.valueOf(3.2)
    }
}

@Service
class TubeFareServiceImpl : TubeFareService {

    override fun calculateFare(eventLog: List<Event>, endLocation: Station): BigDecimal {
        val lastEvent = eventLog.lastOrNull()
        return if (lastEvent is TubeStartEvent) {
            calculateFare(lastEvent.location.zones, endLocation.zones)
        } else MAXIMUM_FARE
    }

    fun calculateFare(startLocationZones: List<Int>, endLocationZones: List<Int>): BigDecimal {
        return when {
            withinZoneOne(startLocationZones, endLocationZones) -> BigDecimal.valueOf(2.5)
            withinSameZone(startLocationZones, endLocationZones) -> BigDecimal.valueOf(2)
            twoZonesIncludingZoneOne(startLocationZones, endLocationZones) -> BigDecimal.valueOf(3)
            twoZonesExcludingZoneOne(startLocationZones, endLocationZones) -> BigDecimal.valueOf(2.25)
            anyThreeZones(startLocationZones, endLocationZones) -> BigDecimal.valueOf(3.2)
            else -> MAXIMUM_FARE
        }
    }

    private fun twoZonesIncludingZoneOne(startLocationZones: List<Int>, endLocationZones: List<Int>): Boolean {
        return (startLocationZones.contains(1) && endLocationZones.contains(2)
                || startLocationZones.contains(2) && endLocationZones.contains(1))
    }

    private fun twoZonesExcludingZoneOne(startLocationZones: List<Int>, endLocationZones: List<Int>): Boolean {
        return (startLocationZones.contains(2) && endLocationZones.contains(3)
                || startLocationZones.contains(3) && endLocationZones.contains(2))
    }

    private fun anyThreeZones(startLocationZones: List<Int>, endLocationZones: List<Int>): Boolean {
        return (startLocationZones.contains(1) && endLocationZones.contains(3)
                || startLocationZones.contains(3) && endLocationZones.contains(1))
    }

    private fun withinSameZone(startLocationZones: List<Int>, endLocationZones: List<Int>): Boolean {
        return startLocationZones.intersect(endLocationZones).isNotEmpty()
    }

    private fun withinZoneOne(startLocationZones: List<Int>, endLocationZones: List<Int>): Boolean {
        return startLocationZones.contains(1) && endLocationZones.contains(1)
    }
}