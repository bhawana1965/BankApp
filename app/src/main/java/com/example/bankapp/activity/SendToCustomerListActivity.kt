package com.example.bankapp.activity

import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankapp.R
import com.example.bankapp.adapters.SendToCustomerAdapter
import com.example.bankapp.database.TransactionsConstants
import com.example.bankapp.database.TransactionsSqliteHelper
import com.example.bankapp.database.UserConstants
import com.example.bankapp.database.UserSqliteHelper
import com.example.bankapp.model.UserModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.example.bankapp.database.UserConstants.UserEntry

import com.example.bankapp.database.TransactionsConstants.TransactionEntry
import kotlinx.android.synthetic.main.activity_customer_data.*
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_send_to_customer_list.*


class SendToCustomerListActivity : AppCompatActivity(), SendToCustomerAdapter.OnUserListener {
    // RecyclerView
    lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: RecyclerView.Adapter<*>
    lateinit var layoutManager: RecyclerView.LayoutManager
    var userArrayList: ArrayList<UserModel>? = null

    // Database
    private var dbHelper: UserSqliteHelper? = null
    var date: String? = null
    var time: String? = null
    var fromUserAccountNo : String? = null
    var toUserAccountNo : String? = null
    var toUserAccountBalance = 0
    var fromUserAccountName: String? = null
    var fromUserAccountBalance = 0
    var transferAmount: String? = null
    var toUserAccountName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_to_customer_list)

        //Setup toolbar
        setSupportActionBar(toolbar_choose_customer)

        val actionbar = supportActionBar

        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "Choose Account"
        }

        // Get Intent
        val bundle = intent.extras
        if (bundle != null) {
            fromUserAccountName = bundle.getString("FROM_USER_NAME")
            fromUserAccountNo = bundle.getString("FROM_USER_ACCOUNT_NO")
            fromUserAccountBalance = bundle.getInt("FROM_USER_ACCOUNT_BALANCE")
            transferAmount = bundle.getString("TRANSFER_AMOUNT")
        }

        // Create ArrayList of Users
        userArrayList = ArrayList<UserModel>()

        // Create Table in the Database
        dbHelper = UserSqliteHelper(this)

        // Show list of items
        recyclerView = findViewById(R.id.send_to_user_list)
        recyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerView.setLayoutManager(layoutManager)
        myAdapter = SendToCustomerAdapter(userArrayList!!, this)
        recyclerView.setAdapter(myAdapter)
    }

    override fun onStart() {
        super.onStart()
        displayDatabaseInfo()
    }

    override fun onUserClick(position: Int) {
        // Insert data into transactions table
        toUserAccountNo = userArrayList!![position].getAccountNumber()
        toUserAccountName = userArrayList!![position].getName()
        toUserAccountBalance = userArrayList!![position].getBalance()
        calculateAmount()
        TransactionsSqliteHelper(this).insertTransferData(
            fromUserAccountName,
            toUserAccountName!!,
            transferAmount,
            1
        )
        Toast.makeText(this, "Transaction Successful!!", Toast.LENGTH_LONG).show()
        startActivity(Intent(this@SendToCustomerListActivity, FinishActivity::class.java))
        finish()
    }

    private fun calculateAmount() {

        val transferAmountInt = transferAmount!!.toInt()
        val remainingAmount = fromUserAccountBalance - transferAmountInt
        val increasedAmount = transferAmountInt + toUserAccountBalance

        // Update amount in the dataBase
        UserSqliteHelper(this).updateAmount(fromUserAccountNo, remainingAmount)
        UserSqliteHelper(this).updateAmount(toUserAccountNo, increasedAmount)
    }

    override fun onBackPressed() {
        val builder_exitButton = AlertDialog.Builder(this@SendToCustomerListActivity)
        builder_exitButton.setTitle("Do you want to cancel the transaction?").setCancelable(false)
            .setPositiveButton(
                "yes"
            ) { dialogInterface, i ->
                // Transactions Cancelled
                val dbHelper = TransactionsSqliteHelper(applicationContext)
                val db: SQLiteDatabase = dbHelper.getWritableDatabase()
                val values = ContentValues()
                values.put(TransactionEntry.COLUMN_FROM_NAME, fromUserAccountName)
                values.put(TransactionEntry.COLUMN_TO_NAME, toUserAccountName)
                values.put(TransactionEntry.COLUMN_STATUS, 0)
                values.put(TransactionEntry.COLUMN_AMOUNT, transferAmount)
                db.insert(TransactionEntry.TABLE_NAME, null, values)
                Toast.makeText(this@SendToCustomerListActivity, "Transaction Cancelled!", Toast.LENGTH_LONG)
                    .show()
                startActivity(Intent(this@SendToCustomerListActivity, AllCustomerActivity::class.java))
                finish()
            }.setNegativeButton("No", null)
        val alertExit = builder_exitButton.create()
        alertExit.show()
    }

    private fun displayDatabaseInfo() {
        // Create or open a database to read from it
        val db: SQLiteDatabase = dbHelper!!.getReadableDatabase()
        val projection = arrayOf(
            UserEntry.COLUMN_USER_NAME,
            UserEntry.COLUMN_USER_ACCOUNT_BALANCE,
            UserEntry.COLUMN_USER_ACCOUNT_NUMBER,
            UserEntry.COLUMN_USER_PHONE_NO,
            UserEntry.COLUMN_USER_EMAIL,
            UserEntry.COLUMN_USER_IFSC_CODE
        )
        val cursor = db.query(
            UserEntry.TABLE_NAME,  // The table to query
            projection,  // The columns to return
            null,  // The columns for the WHERE clause
            null,  // The values for the WHERE clause
            null,  // Don't group the rows
            null,  // Don't filter by row groups
            null // The sort order
        )
        try {
            //  Get the index of each column
            val phoneNoColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_PHONE_NO)
            val emailColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_EMAIL)
            val ifscCodeColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_IFSC_CODE)
            val accountNumberColumnIndex =
                cursor.getColumnIndex(UserEntry.COLUMN_USER_ACCOUNT_NUMBER)
            val nameColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_NAME)
            val accountBalanceColumnIndex =
                cursor.getColumnIndex(UserEntry.COLUMN_USER_ACCOUNT_BALANCE)

            // Iterate through all the rows in the cursor
            while (cursor.moveToNext()) {

                // Use the index to extract the String or Int value of the word
                // at the current row the cursor is on.
                val currentName = cursor.getString(nameColumnIndex)
                val accountNumber = cursor.getString(accountNumberColumnIndex)
                val email = cursor.getString(emailColumnIndex)
                val phoneNumber = cursor.getString(phoneNoColumnIndex)
                val ifscCode = cursor.getString(ifscCodeColumnIndex)
                val accountBalance = cursor.getInt(accountBalanceColumnIndex)

                // Display the values from each column of the current row in the cursor in the TextView
                userArrayList!!.add(
                    UserModel(
                        currentName,
                        accountNumber,
                        phoneNumber,
                        ifscCode,
                        accountBalance,
                        email
                    )
                )
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its resources and makes it invalid.
            cursor.close()
        }
    }
}

