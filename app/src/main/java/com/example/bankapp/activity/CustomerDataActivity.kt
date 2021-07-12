package com.example.bankapp.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import android.content.DialogInterface
import android.widget.Toast
import android.service.autofill.UserData
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.bankapp.R
import kotlinx.android.synthetic.main.activity_all_customer.*
import kotlinx.android.synthetic.main.activity_all_customer.toolbar_all_customer_activity
import kotlinx.android.synthetic.main.activity_customer_data.*
import java.lang.String


class CustomerDataActivity : AppCompatActivity() {

    //Declaring all the variables
    lateinit var name : TextView
    lateinit var accountNumber : TextView
    lateinit var email: TextView
    lateinit var ifscCode : TextView
    lateinit var mobileNumber : TextView
    lateinit var accountBalance : TextView
    lateinit var transferMoney : Button
    lateinit var dialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_data)

        setSupportActionBar(toolbar_customer_data_activity)

        //Setting up the toolbar
        val actionbar = supportActionBar

        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "Details"
        }

        toolbar_customer_data_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        name = findViewById(R.id.name)
        accountNumber = findViewById(R.id.account_no)
        email = findViewById(R.id.email_id)
        ifscCode = findViewById(R.id.ifsc_id)
        mobileNumber = findViewById(R.id.phone_no)
        accountBalance = findViewById(R.id.avail_balance)
        transferMoney = findViewById(R.id.transfer_money)

        val intent = intent
        val extras = intent.extras

        // Extracting the data
        if (extras != null) {
            name.text = extras.getString("NAME")
            accountNumber.text = extras.getString("ACCOUNT_NO")
            email.text = extras.getString("EMAIL")
            mobileNumber.text = extras.getString("PHONE_NO")
            ifscCode.text = extras.getString("IFSC_CODE")
            accountBalance.text = extras.getString("BALANCE")
        } else {
            Log.d("TAG", "Empty Intent")
        }

         this.transferMoney.setOnClickListener { enterAmount() }

    }

    private fun enterAmount() {
        val mBuilder: AlertDialog.Builder = AlertDialog.Builder(this@CustomerDataActivity)
        val mView: View = layoutInflater.inflate(R.layout.dialog_box, null)

        mBuilder.setTitle("Enter Amount").setView(mView).setCancelable(false)

        val mAmount = mView.findViewById<View>(R.id.enter_money) as EditText

        mBuilder.setPositiveButton("SEND", { dialogInterface, i -> })
            .setNegativeButton("CANCEL", { dialog, which ->
                dialog.dismiss()
                transactionCancel()
            })

        dialog = mBuilder.create()
        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(View.OnClickListener {

            // Checking whether amount entered is correct or not
            val currentBalance: Int = String.valueOf(accountBalance.getText()).toInt()
            if (mAmount.text.toString().isEmpty()) {
                mAmount.error = "Amount can't be empty"
            } else if (mAmount.text.toString().toInt() > currentBalance) {
                mAmount.error = "Your account don't have enough balance"
            } else {
                val intent = Intent(this@CustomerDataActivity, SendToCustomerListActivity::class.java)
                intent.putExtra(
                    "FROM_USER_ACCOUNT_NO",
                    accountNumber.getText().toString().toInt()
                )
                // PRIMARY_KEY
                intent.putExtra("FROM_USER_NAME", name.text)
                intent.putExtra("FROM_USER_ACCOUNT_BALANCE", accountBalance.getText())
                intent.putExtra("TRANSFER_AMOUNT", mAmount.text.toString())
                startActivity(intent)
                finish()
            }
        })
    }

    //Function if the transaction get cancelled
    private fun transactionCancel() {
        val builder_exitbutton: AlertDialog.Builder = AlertDialog.Builder(this@CustomerDataActivity)
        builder_exitbutton.setTitle("Do you want to cancel the transaction?").setCancelable(false)
            .setPositiveButton("yes") { dialogInterface, i ->
                Toast.makeText(
                    this@CustomerDataActivity,
                    "Transaction Cancelled!",
                    Toast.LENGTH_LONG
                ).show()
            }.setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                    enterAmount()
                })
        val alertexit: AlertDialog = builder_exitbutton.create()
        alertexit.show()
    }
}