package com.example.bankapp.adapters


import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bankapp.R
import com.example.bankapp.model.UserModel
import java.lang.String


class SendToCustomerAdapter(userArrayList: ArrayList<UserModel>, onUserListener: OnUserListener) :
    RecyclerView.Adapter<SendToCustomerAdapter.ViewHolder>() {

    private val userArrayList: ArrayList<UserModel> = userArrayList
    private val onUserListener: OnUserListener


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.send_to_user_list_item,
                viewGroup,
                false)
        return ViewHolder(view, onUserListener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tag = userArrayList[position]
        viewHolder.userName.text = userArrayList[position].getName()
        viewHolder.userAccountBalance.text = String.format("%d", userArrayList[position].getBalance())
    }

    override fun getItemCount(): Int {
        return userArrayList.size
    }

    inner class ViewHolder(itemView: View, onUserListener: OnUserListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var userName: TextView
        var userAccountBalance: TextView
        var onUserListener: OnUserListener
        override fun onClick(v: View?) {
            onUserListener.onUserClick(adapterPosition)
        }

        init {
            userName = itemView.findViewById(R.id.username)
            userAccountBalance = itemView.findViewById(R.id.amount)
            this.onUserListener = onUserListener
            itemView.setOnClickListener(this)
        }
    }

    interface OnUserListener {
        fun onUserClick(position: Int)
    }

    init {
        this.onUserListener = onUserListener
    }
}