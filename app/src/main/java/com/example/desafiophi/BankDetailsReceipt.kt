package com.example.desafiophi

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.text.SimpleDateFormat

class BankDetailsReceipt : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_details_receipt)

        configureActionBar()
        verifyTransaction()
        passingData()

    }
    private fun passingData (){
        val status = findViewById<TextView>(R.id.textTransfer)
        val value = findViewById<TextView>(R.id.textValue)
        val name = findViewById<TextView>(R.id.textName)
        val date = findViewById<TextView>(R.id.textDateTime)
        val authentication = findViewById<TextView>(R.id.textViewDigits)

        status.text = intent.getStringExtra("status")
        name.text = intent.getStringExtra("name")
        value.text = intent.getStringExtra("value")
        authentication.text = intent.getStringExtra("id")

        val data = intent.getStringExtra("date")
        date.text = convertDateFormart(data)


    }

    @SuppressLint("SimpleDateFormat")
    fun convertDateFormart(data: String?): String{
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val dateFormat = originalFormat.parse(data)
        val simpleDateFormat2 = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        return simpleDateFormat2.format(dateFormat)

    }

    private fun configureActionBar(){
        // chamando a barra de ação
        val actionBar = supportActionBar

        if (actionBar != null) {

            // Personaliza o botão voltar
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
            // mostrando o botão voltar na barra de ação
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun verifyTransaction() {

        val tType = intent.getStringExtra("tType")
        val to = findViewById<TextView>(R.id.textViewTo)

        if (tType.equals("TRANSFEROUT")){
            to.text = "Recebedor"
        }else if (tType.equals("TRANSFERIN")){
            to.text = "Pagador"
        }else if (tType.equals("PIXCASHIN")){
            to.text = "Pagador"
        }else if(tType.equals("PIXCASHOUT")){
            to.text = "Recebedor"
        }else if (tType.equals("BANKSLIPCASHIN")){
            to.text = "Pagador"
        }
    }

    companion object {
        fun getStartIntent(context: Context, status: String?, value: String?, name: String?, date: String?, id: String?, tType: String?): Intent {
            return Intent(context, BankDetailsReceipt::class.java).apply {
                putExtra("status", status)
                putExtra("value", value)
                putExtra("name", name)
                putExtra("date", date)
                putExtra("id", id)
                putExtra("tType", tType)

            }
        }
    }
}