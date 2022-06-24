package com.example.desafiophi.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiophi.R
import com.example.desafiophi.response.ExtractItemResponse
import kotlinx.android.synthetic.main.transaction_list.view.*
import java.text.SimpleDateFormat


class ExtractAdapter (
    private var items: List<ExtractItemResponse>,
    private val onItemClickListener: ((dice: ExtractItemResponse) -> Unit))
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
        private val onItemClickListener: (dice: ExtractItemResponse) -> Unit)
        : RecyclerView.ViewHolder(itemView) {

        private val status = itemView.textViewStatus
        private val value = itemView.textViewValue
        private val name = itemView.textViewName
        private val date = itemView.textViewDate
        private val pix = itemView.transaction_list_Pix_TextView


        fun bindView(dice: ExtractItemResponse) {

            status.text = dice.description
            value.text = dice.amount
            name.text = dice.to
            val data = dice.createdAt
            date.text = convertDateFormart(data)

            configurePixTransaction(dice)


            itemView.setOnClickListener {
                onItemClickListener.invoke(dice)

            }
        }


        fun configurePixTransaction(dice: ExtractItemResponse){
            if (dice.tType.equals("PIXCASHIN") || dice.tType.equals("PIXCASHOUT")) {
                itemView.setBackgroundColor(Color.parseColor("#F8F8F8"))
                pix.visibility = View.VISIBLE
            }
        }

        @SuppressLint("SimpleDateFormat")
        private fun convertDateFormart(data: String?): String {
            val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val dateFormat = originalFormat.parse(data)
            val simpleDateFormat2 = SimpleDateFormat("dd/MM")
            return simpleDateFormat2.format(dateFormat)
        }
    }

}