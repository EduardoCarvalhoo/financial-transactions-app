package com.example.desafiophi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiophi.R
import com.example.desafiophi.model.ExtractItem
import kotlinx.android.synthetic.main.transaction_list.view.*
import java.text.SimpleDateFormat


class ExtractAdapter (
    private var items: List<ExtractItem>,
    private val onItemClickListener: ((dice: ExtractItem) -> Unit))
    : RecyclerView.Adapter<ExtractAdapter.BankTransactionsViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankTransactionsViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_list, parent, false)
        return BankTransactionsViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: BankTransactionsViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class BankTransactionsViewHolder(itemView: View,
        private val onItemClickListener: (dice: ExtractItem) -> Unit)
        : RecyclerView.ViewHolder(itemView) {

        private val status = itemView.textViewStatus
        private val value = itemView.textViewValue
        private val name = itemView.textViewName
        private val date = itemView.textViewDate


        fun bindView(dice: ExtractItem){

            status.text = dice.description
            value.text = dice.amount
            name.text = dice.to
            val data = dice.createdAt

            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            var dateFormat = simpleDateFormat.parse(data)
            val simpleDateFormat2 = SimpleDateFormat("dd/MM")
            date.text = simpleDateFormat2.format(dateFormat)



            itemView.setOnClickListener{
                onItemClickListener.invoke(dice)

            }
        }
    }
}