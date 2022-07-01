package com.example.desafiophi.ui


import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiophi.R
import com.example.desafiophi.adapter.ExtractAdapter
import com.example.desafiophi.response.ExtractItemResponse
import com.example.desafiophi.response.ExtractListResponse
import com.example.desafiophi.response.MyBalanceResponse
import com.example.desafiophi.response.RetrofitConfig
import com.example.desafiophi.utils.ExtractConfig
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var handler: Handler? = null
    var showListener: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settingBalanceVisibility()
        configureProgressBar()
        getBankDetailsMyBalance()
        getBankDetailsMyStatement()


    }

    private fun setupRecyclerView(result: List<ExtractItemResponse>) {
        main_recycler_view.layoutManager = LinearLayoutManager(
            this@MainActivity, RecyclerView.VERTICAL, false
        )
        main_recycler_view.setHasFixedSize(true)
        main_recycler_view.adapter = ExtractAdapter(result) { dice ->
            val intent = BankStatementActivity.getStartIntent(this@MainActivity, dice.id)
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
                    main_balance_text_view.text = myBalance.amount

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
            if (main_recycler_view.adapter != null) {
                main_progressBar.visibility = View.INVISIBLE
            }
            handler?.sendEmptyMessageDelayed(0, 100)

            true
        })

        handler?.sendEmptyMessage(0)
    }

    private fun settingBalanceVisibility() {

        main_visibility_image_button.setOnClickListener {

            if (showListener) {
                main_balance_text_view.visibility = View.VISIBLE
                main_hide_balance_progress_bar.visibility = View.GONE
                main_visibility_image_button.setImageResource(R.drawable.ic_baseline_visibility_24)
                showListener = false
            } else {
                main_balance_text_view.visibility = View.GONE
                main_hide_balance_progress_bar.visibility = View.VISIBLE
                main_visibility_image_button.setImageResource(R.drawable.ic_visibility_off_24)
                showListener = true
            }
        }
    }
}

