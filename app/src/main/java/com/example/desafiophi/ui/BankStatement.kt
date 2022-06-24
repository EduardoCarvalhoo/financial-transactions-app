package com.example.desafiophi.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.desafiophi.R
import com.example.desafiophi.response.ExtractItemDetailResponse
import com.example.desafiophi.response.RetrofitConfig
import com.example.desafiophi.utils.ExtractConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class BankStatement : AppCompatActivity() {

    var recuperarId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_statement)

        configureActionBar()
        detalhes()

    }

    companion object {
        fun getStartIntent(context: Context, id: String?): Intent {
            return Intent(context, BankStatement::class.java).apply {
                putExtra("id", id)
            }
        }
    }

    private fun detalhes(){
        recuperarId = intent.getStringExtra("id")
        val extractService = RetrofitConfig.getRetrofit().getMyStatementDetail(ExtractConfig.TOKEN, recuperarId)
        val call: Call<ExtractItemDetailResponse> = extractService

        call.enqueue(object: Callback<ExtractItemDetailResponse> {
            override fun onResponse(call: Call<ExtractItemDetailResponse>, response: Response<ExtractItemDetailResponse>) {
                if (response.isSuccessful){
                    val item = response.body()
                    if (item != null){
                        val status = findViewById<TextView>(R.id.bankStatement_StatusField_TextView)
                        val value = findViewById<TextView>(R.id.bankStatement_AmountField_TextView)
                        val name = findViewById<TextView>(R.id.bankStatement_NameField_TextView)
                        val date = findViewById<TextView>(R.id.bankStatement_DateTimeField_TextView)
                        val authentication = findViewById<TextView>(R.id.bankStatement_AuthenticationField_TextView)

                        status.text = item.description
                        value.text = item.amount
                        verifyTransaction(item)
                        name.text = item.to
                        date.text = convertDateFormart(item.createdAt)
                        authentication.text = item.authentication

                    }
                }
            }

            override fun onFailure(call: Call<ExtractItemDetailResponse>, t: Throwable) {

            }
        })
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

    private fun verifyTransaction(item: ExtractItemDetailResponse) {
        
        val to = findViewById<TextView>(R.id.bankStatement_RecebedorPagador_TextView)

        if (item.tType.equals("TRANSFEROUT")){
            to.text = "Recebedor"
        }else if (item.tType.equals("TRANSFERIN")){
            to.text = "Pagador"
        }else if (item.tType.equals("PIXCASHIN")){
            to.text = "Pagador"
        }else if(item.tType.equals("PIXCASHOUT")){
            to.text = "Recebedor"
        }else if (item.tType.equals("BANKSLIPCASHIN")){
            to.text = "Pagador"
        }
    }
}