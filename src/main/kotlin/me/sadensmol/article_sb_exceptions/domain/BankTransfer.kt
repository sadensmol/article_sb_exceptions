package me.sadensmol.article_sb_exceptions.domain

import java.math.BigDecimal
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.NotBlank
data class BankTransfer(
    @field:NotBlank val id: String,
    @field:NotBlank val recipient: String,
    @field:DecimalMin("0.50", message = "Amount value cannot be less than 0.5")
    @field:DecimalMax("999999.99", message = "Amount value cannot be more than 999999.99")
    val amount: BigDecimal,
    val reference: String,
)