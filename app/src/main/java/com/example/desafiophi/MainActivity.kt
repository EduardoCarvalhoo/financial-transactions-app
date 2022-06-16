package com.example.desafiophi


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiophi.adapter.ExtractAdapter
import com.example.desafiophi.model.ExtractItem
import com.example.desafiophi.model.ExtractList
import com.example.desafiophi.model.MyBalance
import com.example.desafiophi.model.RetrofitConfig
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        getBankDetailsMyBalance()
        getBankDetailsMyStatement()


    }
    private fun setupRecyclerView(result: List<ExtractItem>){


        rv_recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rv_recyclerView.setHasFixedSize(true)
        rv_recyclerView.adapter = ExtractAdapter(result){ dice ->
            val intent = BankDetailsReceipt.getStartIntent(this@MainActivity, dice.description, dice.amount, dice.to, dice.createdAt, dice.id, dice.tType)
            startActivity(intent)

        }

    }


    private fun getBankDetailsMyBalance(){

        val extractService = RetrofitConfig.getRetrofit().getMyBalance(ExtractConfig.TOKEN)
        val call: Call<MyBalance> = extractService

        call.enqueue(object: Callback<MyBalance> {

            override fun onResponse(call: Call<MyBalance>, response: Response<MyBalance>) {
                if (response.isSuccessful){
                    val myBalance: MyBalance = response.body()!!
                    textViewSaldo.text = myBalance.amount

                }
            }


            override fun onFailure(call: Call<MyBalance>, t: Throwable) {

            }
        })
    }

    private fun getBankDetailsMyStatement(){

        val extractService = RetrofitConfig.getRetrofit().getMyStatement(ExtractConfig.TOKEN)
        val call: Call<ExtractList> = extractService

        call.enqueue(object: Callback<ExtractList> {

            override fun onResponse(call: Call<ExtractList>, response: Response<ExtractList>) {
                if (response.isSuccessful){
                    val result = response.body()?.items
                    if (result != null) {
                        setupRecyclerView(result)
                    }
                }
            }

            override fun onFailure(call: Call<ExtractList>, t: Throwable) {

            }
        })
    }
}