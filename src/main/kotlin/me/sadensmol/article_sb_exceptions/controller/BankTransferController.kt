package me.sadensmol.article_sb_exceptions.controller

import me.sadensmol.article_sb_exceptions.domain.BankTransfer
import me.sadensmol.article_sb_exceptions.domain.BankTransferError
import me.sadensmol.article_sb_exceptions.domain.TerminalException
import me.sadensmol.article_sb_exceptions.service.BankTransferService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/ops")
class BankTransferController(
    private val bankTransferService: BankTransferService
) {
    @PostMapping("/transfer")
    fun transfer(@RequestBody @Validated request: BankTransfer) {
        if (request.recipient == "faulty_test_recipient") throw Exception("Error occurred while retrieving recipient!")

        try{
            bankTransferService.transfer(request.amount, request.recipient)
        }catch (e:BankTransferError) {
            throw TerminalException("Bank transfer isn't possible, sorry, try later again", e.code)
        }

    }

}
