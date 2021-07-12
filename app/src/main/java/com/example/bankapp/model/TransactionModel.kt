package com.example.bankapp.model

class TransactionModel(
    private var fromUser: String,
    private var toUser: String,
    private var amountTransferred: Int,
    private var status: Int
){
    fun getFromUser(): String? {
        return fromUser
    }

    fun setFromUser(fromUser: String?) {
        this.fromUser = fromUser!!
    }

    fun getToUser(): String? {
        return toUser
    }

    fun setToUser(toUser: String) {
       this.toUser = toUser
    }

    fun getAmountTransferred(): Int {
        return amountTransferred
    }

    fun setAmountTransferred(amountTransferred: Int) {
        this.amountTransferred = amountTransferred
    }

    fun getStatus(): Int {
        return status
    }

    fun setStatus(status: Int) {
        this.status = status
    }
}