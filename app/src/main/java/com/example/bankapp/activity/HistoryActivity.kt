package com.example.bankapp.activity

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.view.View
import com.example.bankapp.adapters.TransactionHistoryAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bankapp.R
import com.example.bankapp.database.TransactionsConstants
import com.example.bankapp.database.TransactionsSqliteHelper
import com.example.bankapp.model.TransactionModel
import kotlinx.android.synthetic.main.activity_customer_data.*
import kotlinx.android.synthetic.main.activity_customer_data.toolbar_customer_data_activity
import kotlinx.android.synthetic.main.activity_history.*


class HistoryActivity : AppCompatActivity() {

    //RecyclerView
    lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: RecyclerView.Adapter<*>
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var transactionArrayList: ArrayList<TransactionModel>

    // Database
    private var dbHelper: TransactionsSqliteHelper? = null
    var emptyList: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        setSupportActionBar(toolbar_history_activity)

        val actionbar = supportActionBar

        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "History"
        }

        toolbar_history_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        // Get TextView
        emptyList = findViewById(R.id.empty_text)

        // Create Transaction History List
        transactionArrayList = ArrayList()

        // Create Table in the Database
        dbHelper = TransactionsSqliteHelper(this)


        recyclerView = findViewById(R.id.transactions_list)
        recyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerView.setLayoutManager(layoutManager)
        myAdapter = TransactionHistoryAdapter(this, transactionArrayList)
        recyclerView.setAdapter(myAdapter)

        displayDatabaseInfo()
    }

    private fun displayDatabaseInfo() {
        Log.d("TAG", "displayDataBaseInfo()")

        // Create and/or open a database to read from it
        val db: SQLiteDatabase = dbHelper!!.getReadableDatabase()
        Log.d("TAG", "displayDataBaseInfo()1")
        val projection = arrayOf<String>(
            TransactionsConstants.TransactionEntry.COLUMN_FROM_NAME,
            TransactionsConstants.TransactionEntry.COLUMN_TO_NAME,
            TransactionsConstants.TransactionEntry.COLUMN_AMOUNT,
            TransactionsConstants.TransactionEntry.COLUMN_STATUS
        )

        Log.d("TAG", "displayDataBaseInfo()2")
        val cursor: Cursor = db.query(
            TransactionsConstants.TransactionEntry.TABLE_NAME,  // The table to query
            projection,  // The columns to return
            null,  // The columns for the WHERE clause
            null,  // The values for the WHERE clause
            null,  // Don't group the rows
            null,  // Don't filter by row groups
            null
        ) // The sort order
        try {
            // Figure out the index of each column
            val fromNameColumnIndex: Int =
                cursor.getColumnIndex(TransactionsConstants.TransactionEntry.COLUMN_FROM_NAME)
            val ToNameColumnIndex: Int =
                cursor.getColumnIndex(TransactionsConstants.TransactionEntry.COLUMN_TO_NAME)
            val amountColumnIndex: Int =
                cursor.getColumnIndex(TransactionsConstants.TransactionEntry.COLUMN_AMOUNT)
            val statusColumnIndex: Int =
                cursor.getColumnIndex(TransactionsConstants.TransactionEntry.COLUMN_STATUS)
            Log.d("TAG", "displayDataBaseInfo()3")

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                val fromName: String = cursor.getString(fromNameColumnIndex)
                val toName: String = cursor.getString(ToNameColumnIndex)
                val accountBalance: Int = cursor.getInt(amountColumnIndex)
                val status: Int = cursor.getInt(statusColumnIndex)


                // Display the values from each column of the current row in the cursor in the TextView
                transactionArrayList.add(TransactionModel(fromName, toName, accountBalance, status))
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close()
        }
        if (transactionArrayList!!.isEmpty()) {
            emptyList!!.visibility = View.VISIBLE
        } else {
            emptyList!!.visibility = View.GONE
        }
    }
}