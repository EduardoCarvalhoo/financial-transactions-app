package com.example.desafiophi.ui


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiophi.R
import com.example.desafiophi.adapter.ExtractAdapter
import com.example.desafiophi.databinding.ActivityMainBinding
import com.example.desafiophi.response.ExtractItemResponse
import com.example.desafiophi.response.ExtractListResponse
import com.example.desafiophi.response.MyBalanceResponse
import com.example.desafiophi.response.RetrofitConfig
import com.example.desafiophi.utils.ExtractConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var visibilityOption: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        settingBalanceVisibility()
        getBankDetailsMyBalance()
        getBankDetailsMyStatement()


    }

    private fun setupRecyclerView(result: List<ExtractItemResponse>) {

        with(binding.mainRecyclerView) {
            layoutManager = LinearLayoutManager(
                this@MainActivity, RecyclerView.VERTICAL, false
            )
            setHasFixedSize(true)
            adapter = ExtractAdapter(result) { dice ->
                val intent = BankStatementActivity.getStartIntent(this@MainActivity, dice.id)
                startActivity(intent)

            }
            configureProgressBar()
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
                    binding.mainBalanceTextView.text = myBalance.amount

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

        binding.apply {
            if (mainRecyclerView.adapter != null) {
                mainProgressBar.visibility = View.GONE
            }
        }
    }

    private fun settingBalanceVisibility() {

        binding.apply {
            mainVisibilityImageButton.setOnClickListener {

                if (visibilityOption) {
                    mainBalanceTextView.visibility = View.VISIBLE
                    mainHideBalanceProgressBar.visibility = View.GONE
                    mainVisibilityImageButton.setImageResource(R.drawable.ic_baseline_visibility_24)
                    visibilityOption = false
                } else {
                    mainBalanceTextView.visibility = View.GONE
                    mainHideBalanceProgressBar.visibility = View.VISIBLE
                    mainVisibilityImageButton.setImageResource(R.drawable.ic_visibility_off_24)
                    visibilityOption = true
                }
            }
        }
    }
}

