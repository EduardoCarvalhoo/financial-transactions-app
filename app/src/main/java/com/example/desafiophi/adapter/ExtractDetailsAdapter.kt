package com.example.desafiophi.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiophi.R
import com.example.desafiophi.response.ExtractItemDetailResponse
import com.example.desafiophi.utils.TransferOptions
import kotlinx.android.synthetic.main.item_voucher_details.view.*
import java.text.SimpleDateFormat

class ExtractDetailsAdapter( private var item: List<ExtractItemDetailResponse>
) : RecyclerView.Adapter<ExtractDetailsAdapter.BankTransactionsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankTransactionsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_voucher_details, parent, false)
        return BankTransactionsViewHolder(view)
    }

    override fun onBindViewHolder(holder: BankTransactionsViewHolder, position: Int) {
        holder.bindView(item[position])
    }

    override fun getItemCount(): Int {
        return item.size
    }

    class BankTransactionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val status = itemView.item_voucher_details_transfer_status_text_view
        private val value = itemView.item_voucher_details_transaction_amount_text_view
        private val name = itemView.item_voucher_details_name_text_view
        private val date = itemView.item_voucher_details_value_date_hour_text_view
        private val authentication = itemView.item_voucher_details_authentication_value_text_view

        fun bindView(dice: ExtractItemDetailResponse) {

            status.text = dice.description
            value.text = dice.amount
            name.text = dice.to
            date.text = convertDateFormart(dice.createdAt)
            authentication.text = dice.authentication
            verifyTransaction(dice.tType)
        }

        private fun verifyTransaction(dice: String?) {

            val payerReceiver = itemView.item_voucher_details_payer_receiving_text_view

                when (dice) {
                    TransferOptions.TRANSFEROUT.toString() -> payerReceiver.text =
                        TransferOptions.RECEIVER.transferOptions
                    TransferOptions.TRANSFERIN.toString() -> payerReceiver.text =
                        TransferOptions.PAYER.transferOptions
                    TransferOptions.PIXCASHIN.toString() -> payerReceiver.text =
                        TransferOptions.PAYER.transferOptions
                    TransferOptions.PIXCASHOUT.toString() -> payerReceiver.text =
                        TransferOptions.RECEIVER.transferOptions
                    TransferOptions.BANKSLIPCASHIN.toString() -> payerReceiver.text =
                        TransferOptions.PAYER.transferOptions
                }
        }

        @SuppressLint("SimpleDateFormat")
        fun convertDateFormart(data: String?): String{
            val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val dateFormat = originalFormat.parse(data.toString())
            val simpleDateFormat2 = SimpleDateFormat("dd/MM/yyyy-HH:mm:ss")
            return simpleDateFormat2.format(dateFormat!!)

        }
    }
}
