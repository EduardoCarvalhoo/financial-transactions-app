package com.example.desafiophi.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiophi.R
import com.example.desafiophi.adapter.ExtractDetailsAdapter
import com.example.desafiophi.response.ExtractItemDetailResponse
import com.example.desafiophi.response.RetrofitConfig
import com.example.desafiophi.utils.ExtractConfig
import kotlinx.android.synthetic.main.activity_bank_statement.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BankStatementActivity : AppCompatActivity() {
    var handler: Handler? = null
    private var retrieveId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_statement)


        configureActionBar()
        configureProgressBar()
        voucherIdDetails()

    }

    private fun setupRecyclerView(item: List<ExtractItemDetailResponse>){
        bank_statement_recycler_view.layoutManager = LinearLayoutManager(
            this@BankStatementActivity, RecyclerView.VERTICAL, false)
        bank_statement_recycler_view.setHasFixedSize(true)
        bank_statement_recycler_view.adapter = ExtractDetailsAdapter(item)
    }

    companion object {
        fun getStartIntent(context: Context, id: String?): Intent {
            return Intent(context, BankStatementActivity::class.java).apply {
                putExtra("id", id)
            }
        }
    }

    private fun voucherIdDetails(){

        retrieveId = intent.getStringExtra("id")
        val extractService = RetrofitConfig.getRetrofit().getMyStatementDetail(ExtractConfig.TOKEN, retrieveId)
        val call: Call<ExtractItemDetailResponse> = extractService

        call.enqueue(object: Callback<ExtractItemDetailResponse> {
            override fun onResponse(call: Call<ExtractItemDetailResponse>, response: Response<ExtractItemDetailResponse>) {
                if (response.isSuccessful){
                    val item = response.body()
                    if (item != null) {
                        setupRecyclerView(listOf(item))
                    }
                }
            }

            override fun onFailure(call: Call<ExtractItemDetailResponse>, t: Throwable) {

            }
        })
    }
    private fun configureProgressBar() {
        handler = Handler(Handler.Callback {
            if (bank_statement_recycler_view.adapter != null) {
                bank_statement_progress_bar.visibility = View.INVISIBLE
            }
            handler?.sendEmptyMessageDelayed(0, 100)

            true
        })

        handler?.sendEmptyMessage(0)
    }

    private fun configureActionBar(){

        setSupportActionBar(bank_statement_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)

        
        }
}