package com.example.desafiophi.ui


import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiophi.R
import com.example.desafiophi.adapter.ExtractAdapter
import com.example.desafiophi.response.*
import com.example.desafiophi.utils.ExtractConfig
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var progressStatus = 5
    var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settingBalanceVisibility()
        configureProgressBar()
        getBankDetailsMyBalance()
        getBankDetailsMyStatement()


    }

    private fun setupRecyclerView(result: List<ExtractItemResponse>) {

        rv_recyclerView.layoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rv_recyclerView.setHasFixedSize(true)
        rv_recyclerView.adapter = ExtractAdapter(result) { dice ->
            val intent = BankStatement.getStartIntent(this@MainActivity, dice.id)
            startActivity(intent)

        }
    }


    private fun getBankDetailsMyBalance() {

        val extractService = RetrofitConfig.getRetrofit().getMyBalance(ExtractConfig.TOKEN)
        val call: Call<MyBalanceResponse> = extractService

        call.enqueue(object : Callback<MyBalanceResponse> {

            override fun onResponse(
                call: Call<MyBalanceResponse>,
                response: Response<MyBalanceResponse>
            ) {
                if (response.isSuccessful) {
                    val myBalance: MyBalanceResponse = response.body()!!
                    textViewSaldo.text = myBalance.amount

                }
            }

            override fun onFailure(call: Call<MyBalanceResponse>, t: Throwable) {

            }
        })
    }

    private fun getBankDetailsMyStatement() {

        val extractService = RetrofitConfig.getRetrofit().getMyStatement(ExtractConfig.TOKEN)
        val call: Call<ExtractListResponse> = extractService

        call.enqueue(object : Callback<ExtractListResponse> {

            override fun onResponse(
                call: Call<ExtractListResponse>,
                response: Response<ExtractListResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()?.items
                    if (result != null) {
                        setupRecyclerView(result)
                    }
                }
            }

            override fun onFailure(call: Call<ExtractListResponse>, t: Throwable) {

            }
        })
    }

    private fun configureProgressBar() {
        handler = Handler(Handler.Callback {
            if (rv_recyclerView.adapter == null) {
                progressStatus++
            } else {
                progressStatus = 100
                progressBar.visibility = View.INVISIBLE
            }
            progressBar.progress = progressStatus   // A progressBar recebe os incrementos
            handler?.sendEmptyMessageDelayed(
                0,
                10
            )  // Envia uma Mensagem contendo apenas o valor que, a ser entregue após o tempo especificado.

            true
        })
        handler?.sendEmptyMessage(0)
    }

    fun settingBalanceVisibility() {
        activity_main_button_visibility_off.setOnClickListener {
            textViewSaldo.visibility = View.VISIBLE
            progressBarVisibilitySaldo.visibility = View.INVISIBLE
            activity_main_button_visibility_off.visibility = View.INVISIBLE
            activity_main_button_visibility.visibility = View.VISIBLE
        }
        activity_main_button_visibility.setOnClickListener {
            textViewSaldo.visibility = View.INVISIBLE
            progressBarVisibilitySaldo.visibility = View.VISIBLE
            activity_main_button_visibility_off.visibility = View.VISIBLE
            activity_main_button_visibility.visibility = View.INVISIBLE
        }


    }


}