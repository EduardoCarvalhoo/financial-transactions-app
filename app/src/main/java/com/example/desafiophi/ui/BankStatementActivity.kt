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
import com.example.desafiophi.domain.ExtractDetailItem
import com.example.desafiophi.response.ExtractItemDetailResponse
import com.example.desafiophi.response.RetrofitConfig
import com.example.desafiophi.utils.TOKEN
import com.example.desafiophi.utils.TransferOptions
import com.example.desafiophi.utils.convertDateToFormat
import com.example.desafiophi.utils.showAlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class BankStatementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBankStatementBinding
    private lateinit var extractId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankStatementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        extractId = intent.getStringExtra(ID) ?: ""
        configureActionBar()
        getExtractDetails()
    }

    private fun setupRecyclerView() {
        binding.bankStatementRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                this@BankStatementActivity, RecyclerView.VERTICAL, false
            )
            setHasFixedSize(true)
        }
    }

    companion object {
        private const val ID = "id"
        private const val DATE_TO_CONVERT = "dd/MM/yyyy - HH:mm:ss"
        fun getStartIntent(context: Context, id: String?): Intent {
            return Intent(context, BankStatementActivity::class.java).apply {
                putExtra(ID, id)
            }
        }
    }

    private fun getExtractDetails() {
        binding.bankStatementProgressBar.visibility = View.VISIBLE
        val extractService = RetrofitConfig.getRetrofit().getMyStatementDetail(TOKEN, extractId)
        val call: Call<ExtractItemDetailResponse> = extractService

        call.enqueue(object : Callback<ExtractItemDetailResponse> {
            override fun onResponse(
                call: Call<ExtractItemDetailResponse>,
                response: Response<ExtractItemDetailResponse>
            ) {
                binding.bankStatementProgressBar.visibility = View.GONE
                if (response.isSuccessful && response.body() != null) {
                    val extractItemDetailResponse = response.body() ?: return
                    binding.bankStatementRecyclerView.adapter =
                        ExtractDetailsAdapter(getExtractDetailList(extractItemDetailResponse))
                } else {
                    showAlertDialog(getString(R.string.server_error)) {
                        getExtractDetails()
                    }
                }
            }

            override fun onFailure(call: Call<ExtractItemDetailResponse>, throwable: Throwable) {
                binding.bankStatementProgressBar.visibility = View.GONE
                if (throwable is IOException) {
                    showAlertDialog(getString(R.string.no_internet_connection_error)) {
                        getExtractDetails()
                    }
                } else {
                    showAlertDialog(getString(R.string.generic_error)) {
                        getExtractDetails()
                    }
                }
            }
        })
    }

    private fun getExtractDetailList(
        extractItemDetailResponse: ExtractItemDetailResponse
    ) = listOf(
        ExtractDetailItem(
            title = getString(R.string.bank_statement_type_of_movement_text),
            value = extractItemDetailResponse.description.orEmpty()
        ),
        ExtractDetailItem(
            title = getString(R.string.item_voucher_details_transfer_value_text),
            value = extractItemDetailResponse.amount.orEmpty()
        ),
        ExtractDetailItem(
            title = TransferOptions.valueOfOrNull(extractItemDetailResponse.tType),
            value = extractItemDetailResponse.to.orEmpty()
        ),
        ExtractDetailItem(
            title = getString(R.string.item_voucher_details_date_time_text),
            value = extractItemDetailResponse.createdAt.orEmpty()
                .convertDateToFormat(DATE_TO_CONVERT) ?: ""
        ),
        ExtractDetailItem(
            title = getString(R.string.item_voucher_details_authentication_text),
            value = extractItemDetailResponse.authentication.orEmpty()
        )
    )

    private fun configureActionBar() {
        setSupportActionBar(binding.bankStatementToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
    }

}