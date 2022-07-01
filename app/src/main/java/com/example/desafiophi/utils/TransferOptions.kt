package com.example.desafiophi.utils

enum class TransferOptions (val transferOptions: String?){
    TRANSFEROUT("TRANSFEROUT"), TRANSFERIN("TRANSFERIN"), PIXCASHIN("PIXCASHIN") , PIXCASHOUT("PIXCASHOUT"),
    BANKSLIPCASHIN("BANKSLIPCASHIN"), RECEIVER("Recebedor"), PAYER("Pagador")
}