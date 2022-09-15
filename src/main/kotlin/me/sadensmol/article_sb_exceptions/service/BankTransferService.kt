package me.sadensmol.article_sb_exceptions.service

import me.sadensmol.article_sb_exceptions.domain.BankTransferError
import me.sadensmol.article_sb_exceptions.domain.NotFoundException
import me.sadensmol.article_sb_exceptions.domain.TerminalException
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class BankTransferService(private val bankClient: BankApiClient) {

    fun transfer(amount: BigDecimal, recipient:String) {
        if (recipient == "faulty_bl_test_recipient") throw TerminalException("Something wrong happened in BL!","BTS_OO1")
        if (recipient == "not_found_test_recipient") throw NotFoundException("Recipient not found","BTS_OO1")

        try{
            bankClient.sendTransfer(amount, 1)
        } catch (e: BankApiClient.BankIsClosedException) {
            throw BankTransferError ("Could not send transfer", code = "BTS_011")
        } catch (e: BankApiClient.TargetAccountIsInvalid) {
            throw BankTransferError ("Could not send transfer", code = "BTS_012")
        }
    }
}