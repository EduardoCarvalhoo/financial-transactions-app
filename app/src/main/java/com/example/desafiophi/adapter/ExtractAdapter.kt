package com.example.desafiophi.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiophi.R
import com.example.desafiophi.databinding.ItemTransactionBinding
import com.example.desafiophi.response.ExtractItemResponse
import com.example.desafiophi.utils.TransferOptions
import java.text.SimpleDateFormat


class ExtractAdapter(
    private var items: List<ExtractItemResponse>,
    private val onItemClickListener: ((dice: ExtractItemResponse) -> Unit)
) : RecyclerView.Adapter<ExtractAdapter.BankTransactionsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankTransactionsViewHolder {

        val view =
            ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BankTransactionsViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: BankTransactionsViewHolder, position: Int) {
        holder.bindView(items[position])

    }

    override fun getItemCount(): Int {
        return items.size
    }

    class BankTransactionsViewHolder(
        binding: ItemTransactionBinding,
        private val onItemClickListener: (dice: ExtractItemResponse) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val status = binding.itemTransactionStatusTextView
        private val value = binding.itemTransactionValueTextView
        private val name = binding.itemTransactionNameTextView
        private val date = binding.itemTrasactionDateTextView
        private val pix = binding.itemTransactionPixTextView

        fun bindView(dice: ExtractItemResponse) {

            status.text = dice.description
            value.text = dice.amount
            name.text = dice.to
            date.text = convertDateFormart(dice.createdAt)

            configurePixTransaction(dice)


            itemView.setOnClickListener {
                onItemClickListener.invoke(dice)

            }
        }

        private fun configurePixTransaction(dice: ExtractItemResponse) {
            if (dice.tType.equals(TransferOptions.PIXCASHIN.transferOptions) || dice.tType.equals(
                    TransferOptions.PIXCASHOUT.transferOptions
                )
            ) {
                itemView.setBackgroundResource(R.color.light_grey)
                pix.visibility = View.VISIBLE
            }
        }

        @SuppressLint("SimpleDateFormat")
        private fun convertDateFormart(data: String?): String {
            val originalFormat = SimpleDateFormat(ORIGINAL_DATE_FORMAT)
            val dateFormat = originalFormat.parse(data!!)
            val convertedFormat = SimpleDateFormat(CONVERTED_FORMAT)
            return convertedFormat.format(dateFormat!!)
        }

        companion object {
            private const val ORIGINAL_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
            private const val CONVERTED_FORMAT = "dd/MM"
        }
    }
}