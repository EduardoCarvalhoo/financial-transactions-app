package com.example.desafiophi.utils

enum class TransferOptions(val value: String) {
    TRANSFEROUT("TRANSFEROUT"), TRANSFERIN("TRANSFERIN"),
    PIXCASHIN("PIXCASHIN"), PIXCASHOUT("PIXCASHOUT"),
    BANKSLIPCASHIN("BANKSLIPCASHIN");

    companion object {
        fun valueOfOrNull(transferOption: String?): String? {
            return when (transferOption) {
                TRANSFEROUT.value -> TransactionAgent.RECEIVER.value
                TRANSFERIN.value -> TransactionAgent.PAYER.value
                PIXCASHIN.value -> TransactionAgent.PAYER.value
                PIXCASHOUT.value -> TransactionAgent.RECEIVER.value
                BANKSLIPCASHIN.value -> TransactionAgent.PAYER.value
                else -> null
            }
        }
    }
}