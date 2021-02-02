# The Oyster Card Problem

You are required to model the following fare card system which is a limited version of London’s Oyster card system. At
the end of the test, you should be able to demonstrate a user loading a card with £30, and taking the following trips,
and then viewing the balance.

- Tube Holborn to Earl’s Court
- 328 bus from Earl’s Court to Chelsea
- Tube Earl’s Court to Hammersmith

When the user passes through the inward barrier at the station, their oyster card is charged the maximum fare.

When they pass out of the barrier at the exit station, the fare is calculated and the maximum fare transaction removed
and replaced with the real transaction (in this way, if the user doesn’t swipe out, they are charged the maximum fare).

All bus journeys are charged at the same price.

The system should favour the customer where more than one fare is possible for a given journey. E.g. Holburn to Earl’s
Court is charged at £2.50.

For the purposes of this test use the following data:

Stations and zones:

Station Zone(s)

- Holborn 1
- Earl’s Court 1, 2
- Wimbledon 3
- Hammersmith 2

Fares:

Journey Fare

- Anywhere in Zone 1 £2.50
- Any one zone outside zone 1 £2.00
- Any two zones including zone 1 £3.00
- Any two zones excluding zone 1 £2.25
- Any three zones £3.20
- Any bus journey £1.80

The maximum possible fare is therefore £3.20.

## Assumptions

- The code only looks at the previous event for the start of a tube journey. This could be extended to look for tube start
  events without a corresponding tube end.
- When ending a tube journey the maximum fare which was charged at the start of the journey is refunded. This refund
  event is kept in the event log for auditing purposes.
- Invalid tube journeys are charged the maximum fare. Extra rules could be added for edge cases.

## Technical

### Requirements

Requires [Java 11](https://adoptopenjdk.net/).

### Run the tests

```shell
./mvnw clean test
```

### Execute the main class

The main class demonstrates the example described in the problem definition.

```shell
./mvnw spring-boot:run
```