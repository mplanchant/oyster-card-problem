package uk.co.logiccache.oyster.domain

enum class Station(val zones: List<Int>) {
    HAMMERSMITH(listOf(2)),
    EARLS_COURT(listOf(1, 2)),
    WIMBLEDON(listOf(3)),
    HOLBORN(listOf(1)),
    CHELSEA(listOf());
}