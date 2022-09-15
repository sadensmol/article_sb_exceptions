package me.sadensmol.article_sb_exceptions.controller

import me.sadensmol.article_sb_exceptions.util.TEST_BANK_TRANSFER
import me.sadensmol.article_sb_exceptions.util.TEST_FAULTY_BL_RECIPIENT_BANK_TRANSFER
import me.sadensmol.article_sb_exceptions.util.TEST_NON_VALID_AMOUNT_BANK_TRANSFER
import me.sadensmol.article_sb_exceptions.util.TEST_FAULTY_RECIPIENT_BANK_TRANSFER
import me.sadensmol.article_sb_exceptions.util.TEST_NOT_FOUND_RECIPIENT_BANK_TRANSFER
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
internal class BankTransferControllerTest(
    @Autowired private val webTestClient: WebTestClient,
) {

    @TestConfiguration
    class TestConfig(private val context: ApplicationContext) {
        @Bean
        fun transferTestClient(): WebTestClient =
            WebTestClient
                .bindToApplicationContext(context)
                .build()
    }

    @Test
    fun `given transfer request with wrong amount when transfer then return bad request`() {
        webTestClient
            .post()
            .uri("/api/v1/ops/transfer")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TEST_NON_VALID_AMOUNT_BANK_TRANSFER)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .json("""{"code":"validation_error","message":"Amount value cannot be less than 0.5"}""")
    }

    @Test
    fun `given transfer request with faulty recipient when transfer then return server error`() {
        webTestClient
            .post()
            .uri("/api/v1/ops/transfer")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TEST_FAULTY_RECIPIENT_BANK_TRANSFER)
            .exchange()
            .expectStatus().is5xxServerError
            .expectBody()
            .json("""{"code":"internal_error","message":"Some error occurred","details":"Error occurred while retrieving recipient!"}""")
    }

    @Test
    fun `given transfer request with business logic faulty recipient when transfer then return server error`() {
        webTestClient
            .post()
            .uri("/api/v1/ops/transfer")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TEST_FAULTY_BL_RECIPIENT_BANK_TRANSFER)
            .exchange()
            .expectStatus().is5xxServerError
            .expectBody()
            .json("""{"code":"BTS_OO1","message":"Something wrong happened in BL!"}""")
    }

    @Test
    fun `given transfer request with not found recipient when transfer then return server error`() {
        webTestClient
            .post()
            .uri("/api/v1/ops/transfer")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TEST_NOT_FOUND_RECIPIENT_BANK_TRANSFER)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .json("""{"code":"BTS_OO1","message":"Recipient not found"}""")
    }
    @Test
    fun `given correct transfer request when transfer then return error`() {
        webTestClient
            .post()
            .uri("/api/v1/ops/transfer")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TEST_BANK_TRANSFER)
            .exchange()
            .expectStatus().is5xxServerError
            .expectBody()
            .json("""{"code":"BTS_OO3","message":"Recipient not found"}""")
    }
}