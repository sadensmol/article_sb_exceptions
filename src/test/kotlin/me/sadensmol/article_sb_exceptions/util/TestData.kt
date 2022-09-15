package me.sadensmol.article_sb_exceptions.util

const val TEST_BANK_TRANSFER = """
{
    "id":"test_id",
    "recipient":"test_recipient",
    "reference":"test_reference",
    "amount":"12.13"
}
"""

const val TEST_NON_VALID_AMOUNT_BANK_TRANSFER = """
{
    "id":"test_id",
    "recipient":"test_recipient",
    "reference":"test_reference",
    "amount":"00.00"
}
"""

const val TEST_FAULTY_RECIPIENT_BANK_TRANSFER = """
{
    "id":"test_id",
    "recipient":"faulty_test_recipient",
    "reference":"test_reference",
    "amount":"12.33"
}
"""

const val TEST_FAULTY_BL_RECIPIENT_BANK_TRANSFER = """
{
    "id":"test_id",
    "recipient":"faulty_bl_test_recipient",
    "reference":"test_reference",
    "amount":"12.33"
}
"""

const val TEST_NOT_FOUND_RECIPIENT_BANK_TRANSFER = """
{
    "id":"test_id",
    "recipient":"not_found_test_recipient",
    "reference":"test_reference",
    "amount":"12.33"
}
"""





