package uk.co.logiccache.oyster.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class CustomerTest {

    @Test
    internal fun new_customer_has_zero_balance() {
        assertThat(Customer("Harry").balance)
                .isEqualTo(BigDecimal.ZERO)
    }
}