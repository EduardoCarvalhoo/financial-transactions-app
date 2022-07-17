package com.example.desafiophi.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiophi.R
import com.example.desafiophi.adapter.ExtractDetailsAdapter
import com.example.desafiophi.databinding.ActivityBankStatementBinding
import com.example.desafiophi.response.ExtractItemDetailResponse
import com.example.desafiophi.response.RetrofitConfig
import com.example.desafiophi.utils.ExtractConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BankStatementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBankStatementBinding
    private var retrieveId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankStatementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureActionBar()
        voucherIdDetails()

    }

    private fun setupRecyclerView(item: List<ExtractItemDetailResponse>) {
        with(binding.bankStatementRecyclerView) {
            layoutManager = LinearLayoutManager(
                this@BankStatementActivity, RecyclerView.VERTICAL, false
            )
            setHasFixedSize(true)
            adapter = ExtractDetailsAdapter(item)
            configureProgressBar()
        }
    }

    companion object {
        private const val ID = "id"
        fun getStartIntent(context: Context, id: String?): Intent {
            return Intent(context, BankStatementActivity::class.java).apply {
                putExtra(ID, id)
            }
        }
    }

    private fun voucherIdDetails() {

        retrieveId = intent.getStringExtra(ID)
        val extractService =
            RetrofitConfig.getRetrofit().getMyStatementDetail(ExtractConfig.TOKEN, retrieveId)
        val call: Call<ExtractItemDetailResponse> = extractService

        call.enqueue(object : Callback<ExtractItemDetailResponse> {
            override fun onResponse(
                call: Call<ExtractItemDetailResponse>,
                response: Response<ExtractItemDetailResponse>
            ) {
                if (response.isSuccessful) {
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

        binding.apply {
            if (bankStatementRecyclerView.adapter != null) {
                bankStatementProgressBar.visibility = View.GONE
            }
        }
    }

    private fun configureActionBar() {

        binding.apply {
            setSupportActionBar(bankStatementToolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = null
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        }
    }
}