package com.example.bankapp.activity
import android.database.Cursor
import com.example.bankapp.database.UserConstants.UserEntry
import com.example.bankapp.database.UserSqliteHelper
import com.example.bankapp.adapters.UserListAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AppCompatActivity
import com.example.bankapp.R
import com.example.bankapp.model.UserModel
import kotlinx.android.synthetic.main.activity_all_customer.*


class AllCustomerActivity : AppCompatActivity() {
    // RecyclerView
    lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: UserListAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager
    var userArrayList = ArrayList<UserModel>()

    // Database
    private var dbHelper: UserSqliteHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_customer)

        //Set up the toolbar
        setSupportActionBar(toolbar_all_customer_activity)

        val actionbar = supportActionBar

        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "All Customers"
        }

        toolbar_all_customer_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        // Create ArrayList of Users
        userArrayList = ArrayList<UserModel>()

        // Create Table in the Database
        dbHelper = UserSqliteHelper(this)

        // Read Data from DataBase
        displayDatabaseInfo()

        // Show list of items
        recyclerView = findViewById(R.id.recyclerUserList)
        layoutManager = LinearLayoutManager(this)
        recyclerView.setLayoutManager(layoutManager)
        myAdapter = UserListAdapter(this, userArrayList!!)
        recyclerView.setAdapter(myAdapter)
    }

    private fun displayDatabaseInfo() {
        userArrayList!!.clear()

        val cursor: Cursor = UserSqliteHelper(this).readAllData()

        val phoneNoColumnIndex: Int = cursor.getColumnIndex(UserEntry.COLUMN_USER_PHONE_NO)

        val emailColumnIndex: Int = cursor.getColumnIndex(UserEntry.COLUMN_USER_EMAIL)

        val ifscCodeColumnIndex: Int = cursor.getColumnIndex(UserEntry.COLUMN_USER_IFSC_CODE)

        val accountNumberColumnIndex: Int =
            cursor.getColumnIndex(UserEntry.COLUMN_USER_ACCOUNT_NUMBER)

        val nameColumnIndex: Int = cursor.getColumnIndex(UserEntry.COLUMN_USER_NAME)

        val accountBalanceColumnIndex: Int =
            cursor.getColumnIndex(UserEntry.COLUMN_USER_ACCOUNT_BALANCE)

        while (cursor.moveToNext()) {
            val currentName: String = cursor.getString(nameColumnIndex)
            val accountNumber: String = cursor.getString(accountNumberColumnIndex)
            val email: String = cursor.getString(emailColumnIndex)
            val phoneNumber: String = cursor.getString(phoneNoColumnIndex)
            val ifscCode: String = cursor.getString(ifscCodeColumnIndex)
            val accountBalance: Int = cursor.getInt(accountBalanceColumnIndex)

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
    }
}