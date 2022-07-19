package com.example.desafiophi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiophi.R
import com.example.desafiophi.databinding.ItemTransactionBinding
import com.example.desafiophi.response.ExtractItemResponse
import com.example.desafiophi.utils.TransferOptions
import com.example.desafiophi.utils.convertDateToFormat


class ExtractAdapter(
    private val items: List<ExtractItemResponse>,
    private val onItemClickListener: (item: ExtractItemResponse) -> Unit
) : RecyclerView.Adapter<ExtractAdapter.BankTransactionsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankTransactionsViewHolder {
        val view = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
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

        fun bindView(item: ExtractItemResponse) {
            status.text = item.description
            value.text = item.amount
            name.text = item.to
            date.text = item.createdAt?.convertDateToFormat(CONVERTED_FORMAT) ?: ""

            configurePixTransaction(item)
            itemView.setOnClickListener {
                onItemClickListener.invoke(item)
            }
        }

        private fun configurePixTransaction(item: ExtractItemResponse) {
            if (item.tType == TransferOptions.PIXCASHIN.value ||
                item.tType == TransferOptions.PIXCASHOUT.value
            ) {
                itemView.setBackgroundResource(R.color.light_grey)
                pix.visibility = View.VISIBLE
            }
        }

        companion object {
            private const val CONVERTED_FORMAT = "dd/MM"
        }
    }
}