package me.sadensmol.article_sb_exceptions.service

import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDateTime

@Component
class BankApiClient {

    fun sendTransfer(amount: BigDecimal, accountNumber: Int) {

        if (accountNumber == 1) throw TargetAccountIsInvalid("Sorry this account doesn't exist")

        throw BankIsClosedException(
            "Sorry but our bank is closed!",
            LocalDateTime.now(), LocalDateTime.now().plusHours(8)
        )
    }

    class BankIsClosedException(message: String, val from: LocalDateTime, val to: LocalDateTime) : Exception(message)
    class TargetAccountIsInvalid(message: String) : Exception(message)

}