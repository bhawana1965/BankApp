package com.example.bankapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bankapp.R
import com.example.bankapp.model.UserModel
import kotlinx.android.synthetic.main.customer_single_row.view.*
import android.service.autofill.UserData
import android.content.Intent
import com.example.bankapp.activity.CustomerDataActivity
import java.lang.String


class UserListAdapter(val context: Context, val items : ArrayList<UserModel>) : RecyclerView.Adapter<UserListAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListAdapter.ViewHolder {

        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.customer_single_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserListAdapter.ViewHolder, position: Int) {
        holder.itemView.tag = items[position]
        holder.customerName.text = items[position].getName()
        holder.customerAccountNumber.text = items[position].getAccountNumber()
        holder.customerAccountBalance.text = items[position].getBalance().toString()



        holder.itemView.setOnClickListener(View.OnClickListener { v ->
            val intent = Intent(v.context, CustomerDataActivity::class.java)
            intent.putExtra("ACCOUNT_NO", items[position].getAccountNumber())
            intent.putExtra("NAME", items[position].getName())
            intent.putExtra("EMAIL", items[position].getEmail())
            intent.putExtra("IFSC_CODE", items[position].getIfscCode())
            intent.putExtra("PHONE_NO", items[position].getPhoneNumber())
            intent.putExtra("BALANCE", String.valueOf(items[position].getBalance()))
            v.context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val customerName = view.textViewCustomerName
        val customerAccountNumber = view.textViewAccountPerson
        val customerAccountBalance = view.txtAmount

    }

}