package com.example.desafiophi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiophi.databinding.ItemVoucherDetailsBinding
import com.example.desafiophi.domain.ExtractDetailItem


class ExtractDetailsAdapter(
    private val items: List<ExtractDetailItem>
) : RecyclerView.Adapter<ExtractDetailsAdapter.BankTransactionsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankTransactionsViewHolder {
        val view = ItemVoucherDetailsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BankTransactionsViewHolder(view)
    }

    override fun onBindViewHolder(holder: BankTransactionsViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class BankTransactionsViewHolder(binding: ItemVoucherDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val title = binding.itemVoucherDetailsTitleTextView
        private val value = binding.itemVoucherDetailsValueTextView

        fun bindView(item: ExtractDetailItem) {
            title.text = item.title
            value.text = item.value
        }
    }
}
