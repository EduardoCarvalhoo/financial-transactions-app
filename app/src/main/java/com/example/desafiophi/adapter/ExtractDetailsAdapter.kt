package com.example.desafiophi.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiophi.databinding.ItemVoucherDetailsBinding
import com.example.desafiophi.response.ExtractItemDetailResponse
import com.example.desafiophi.utils.TransferOptions
import java.text.SimpleDateFormat


class ExtractDetailsAdapter(
    private var item: List<ExtractItemDetailResponse>
) : RecyclerView.Adapter<ExtractDetailsAdapter.BankTransactionsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankTransactionsViewHolder {
        val view = ItemVoucherDetailsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return BankTransactionsViewHolder(view)

    }

    override fun onBindViewHolder(holder: BankTransactionsViewHolder, position: Int) {
        holder.bindView(item[position])
    }

    override fun getItemCount(): Int {
        return item.size
    }

    class BankTransactionsViewHolder(
        binding: ItemVoucherDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val status = binding.itemVoucherDetailsTransferStatusTextView
        private val value = binding.itemVoucherDetailsTransactionAmountTextView
        private val name = binding.itemVoucherDetailsNameTextView
        private val date = binding.itemVoucherDetailsValueDateHourTextView
        private val authentication = binding.itemVoucherDetailsAuthenticationValueTextView
        private val payerReceiver = binding.itemVoucherDetailsPayerReceivingTextView

        fun bindView(dice: ExtractItemDetailResponse) {

            status.text = dice.description
            value.text = dice.amount
            name.text = dice.to
            date.text = convertDateFormart(dice.createdAt)
            authentication.text = dice.authentication
            verifyTransaction(dice.tType)

        }

        private fun verifyTransaction(dice: String?) {

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
        private fun convertDateFormart(data: String?): String {

            val originalFormat =
                SimpleDateFormat(ORIGINAL_DATE_FORMAT)
            val dateFormat = originalFormat.parse(data!!)
            val simpleDateFormat2 =
                SimpleDateFormat(MANUAL_FORMATTED_DATE)
            return simpleDateFormat2.format(dateFormat!!)

        }

        companion object{
            private const val ORIGINAL_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
            private const val MANUAL_FORMATTED_DATE = "dd/MM/yyyy-HH:mm:ss"
        }

    }
}
