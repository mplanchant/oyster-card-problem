package uk.co.logiccache.oyster

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.system.CapturedOutput
import org.springframework.boot.test.system.OutputCaptureExtension
import uk.co.logiccache.oyster.service.OysterCardService
import uk.co.logiccache.oyster.service.TubeFareService

@SpringBootTest
@ExtendWith(OutputCaptureExtension::class)
class OysterCardProblemKotlinApplicationTests @Autowired constructor(
    private val tubeFareService: TubeFareService,
    private val oysterCardService: OysterCardService
) {

    @Test
    fun context_loads() {
        assertThat(oysterCardService).isNotNull
        assertThat(tubeFareService).isNotNull
    }

    @Test
    fun test_main(output: CapturedOutput) {
        main(arrayOf())
        assertThat(output)
            .contains("Started OysterCardProblemKotlinApplicationTests")
    }
}