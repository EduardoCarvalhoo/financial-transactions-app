package com.example.desafiophi.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiophi.R
import com.example.desafiophi.adapter.ExtractAdapter
import com.example.desafiophi.databinding.ActivityMainBinding
import com.example.desafiophi.response.ExtractListResponse
import com.example.desafiophi.response.MyBalanceResponse
import com.example.desafiophi.response.RetrofitConfig
import com.example.desafiophi.utils.TOKEN
import com.example.desafiophi.utils.showAlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        settingBalanceVisibility()
        getBankDetailsMyBalance()
        getBankDetailsMyStatement()
    }

    private fun setupRecyclerView() {
        with(binding.mainRecyclerView) {
            layoutManager = LinearLayoutManager(
                this@MainActivity, RecyclerView.VERTICAL, false
            )
            setHasFixedSize(true)
        }
    }

    private fun getBankDetailsMyBalance() {
        binding.mainProgressBar.isVisible = true
        val extractService = RetrofitConfig.getRetrofit().getMyBalance(TOKEN)
        val call: Call<MyBalanceResponse> = extractService

        call.enqueue(object : Callback<MyBalanceResponse> {
            override fun onResponse(
                call: Call<MyBalanceResponse>,
                response: Response<MyBalanceResponse>
            ) {
                binding.mainProgressBar.isVisible = false
                if (response.isSuccessful && response.body() != null) {
                    val myBalance: MyBalanceResponse? = response.body()
                    binding.mainBalanceTextView.text = myBalance?.amount.orEmpty()
                } else {
                    binding.mainBalanceTextView.text = getString(R.string.balance_error_message)
                }
            }

            override fun onFailure(call: Call<MyBalanceResponse>, t: Throwable) {
                binding.mainProgressBar.isVisible = false
                binding.mainBalanceTextView.text = getString(R.string.balance_error_message)
            }
        })
    }

    private fun getBankDetailsMyStatement() {
        binding.mainProgressBar.isVisible = true
        val extractService = RetrofitConfig.getRetrofit().getMyStatement(TOKEN)
        val call: Call<ExtractListResponse> = extractService

        call.enqueue(object : Callback<ExtractListResponse> {
            override fun onResponse(
                call: Call<ExtractListResponse>,
                response: Response<ExtractListResponse>
            ) {
                binding.mainProgressBar.isVisible = false
                handleBankDetailsResponse(response)
            }

            override fun onFailure(call: Call<ExtractListResponse>, t: Throwable) {
                binding.mainProgressBar.isVisible = false
                handleBankDetailsFailure(t)
            }
        })
    }

    private fun handleBankDetailsFailure(throwable: Throwable) {
        if (throwable is IOException) {
            showAlertDialog(getString(R.string.no_internet_connection_error)) {
                getBankDetailsMyStatement()
            }
        } else {
            showAlertDialog(getString(R.string.generic_error)) {
                getBankDetailsMyStatement()
            }
        }
    }

    private fun handleBankDetailsResponse(response: Response<ExtractListResponse>) {
        if (response.isSuccessful && response.body() != null) {
            binding.mainRecyclerView.adapter = ExtractAdapter(
                response.body()?.items ?: return
            ) { extractItemResponse ->
                val intent = BankStatementActivity.getStartIntent(
                    this@MainActivity, extractItemResponse.id
                )
                startActivity(intent)
            }
        } else {
            showAlertDialog(getString(R.string.server_error)) {
                getBankDetailsMyStatement()
            }
        }
    }

    private fun settingBalanceVisibility() {
        binding.apply {
            mainVisibilityImageButton.setOnClickListener {
                mainBalanceTextView.isVisible = !mainBalanceTextView.isVisible
                mainHideBalanceProgressBar.isVisible = !mainHideBalanceProgressBar.isVisible
                if (mainBalanceTextView.isVisible) {
                    mainVisibilityImageButton.setImageResource(R.drawable.ic_baseline_visibility_24)
                } else {
                    mainVisibilityImageButton.setImageResource(R.drawable.ic_visibility_off_24)
                }
            }
        }
    }
}

