package com.example.desafiophi

enum class TransferOptions (val transferOptions: String?){
    TRANSFEROUT("TRANSFEROUT"), TRANSFERIN("TRANSFERIN"), PIXCASHIN("PIXCASHIN") , PIXCASHOUT("PIXCASHOUT"),
    BANKSLIPCASHIN("BANKSLIPCASHIN"), RECEIVER("Recebedor"), PAYER("Pagador")
}