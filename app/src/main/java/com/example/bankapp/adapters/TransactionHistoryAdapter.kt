package com.example.bankapp.adapters

import androidx.annotation.NonNull


import android.content.Context
import android.content.Intent
import android.graphics.Color

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import androidx.core.content.ContextCompat
import com.example.bankapp.R
import com.example.bankapp.model.TransactionModel


class TransactionHistoryAdapter(
    context: Context?,
    private val transactionArrayList: ArrayList<TransactionModel>
) :
    RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var fromName: TextView = itemView.findViewById(R.id.t_from_name)
        var toName: TextView = itemView.findViewById(R.id.t_to_name)
        var amountTransferred: TextView = itemView.findViewById(R.id.t_amount)
        var cardView: CardView = itemView.findViewById(R.id.transaction_card_view)

        init {
            itemView.setOnClickListener {
                // Nothing Happened right now
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.transaction_history_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tag = transactionArrayList[position]
        viewHolder.fromName.setText(transactionArrayList[position].getFromUser())
        viewHolder.toName.setText(transactionArrayList[position].getToUser())
        viewHolder.amountTransferred.text =
            String.format("%d", transactionArrayList[position].getAmountTransferred())
        if (transactionArrayList[position].getStatus() === 1) {
            //To change the background color according to the position value
            if(position % 2 ==0){
                viewHolder.cardView.setCardBackgroundColor(Color.parseColor("#EBEBEB"))
            }else{
                viewHolder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            }
        } else {
            viewHolder.cardView.setCardBackgroundColor(Color.argb(100, 239, 100, 100))
        }

    }

    override fun getItemCount(): Int {
        return transactionArrayList.size
    }
}