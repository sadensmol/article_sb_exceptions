package me.sadensmol.article_sb_exceptions.domain

class BankTransferError(message: String? = null, code: String? = null) : BusinessError(message, code)

