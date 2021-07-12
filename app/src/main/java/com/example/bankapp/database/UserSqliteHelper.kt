package com.example.bankapp.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.bankapp.database.UserConstants.UserEntry


class UserSqliteHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    var TABLE_NAME = UserEntry.TABLE_NAME
    override fun onCreate(db: SQLiteDatabase) {
        // Create a String that contains the SQL statement to create the pets table
        val SQL_CREATE_USER_TABLE = ("CREATE TABLE " + UserEntry.TABLE_NAME + " ("
                + UserEntry.COLUMN_USER_ACCOUNT_NUMBER + " VARCHAR, "
                + UserEntry.COLUMN_USER_NAME + " VARCHAR, "
                + UserEntry.COLUMN_USER_EMAIL + " VARCHAR, "
                + UserEntry.COLUMN_USER_IFSC_CODE + " VARCHAR, "
                + UserEntry.COLUMN_USER_PHONE_NO + " VARCHAR, "
                + UserEntry.COLUMN_USER_ACCOUNT_BALANCE + " INTEGER NOT NULL);")

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_USER_TABLE)

        // Insert Into Table
        db.execSQL("insert into $TABLE_NAME values('006078','Bhawana Sharma', 'bhawanaSha@gmail.com','4569','7805681238', 105000)")
        db.execSQL("insert into $TABLE_NAME values('085469','Dhruv Sharma', 'dhruvSha@gmail.com','7852','9990641231', 155000)")
        db.execSQL("insert into $TABLE_NAME values('000254','Bhanu Pratap', 'bhanuPratap@gmail.com','9632','8598645890', 12000)")
        db.execSQL("insert into $TABLE_NAME values('012548','Babblu Singh', 'babbluSingh@gmail.com','8569','7990640038', 4000)")
        db.execSQL("insert into $TABLE_NAME values('458212','Preeti Kumari', 'preetiKumari@gmail.com','9856','9190648969', 80000)")
        db.execSQL("insert into $TABLE_NAME values('000936','Chanchal Gupta', 'chanchalGup@gmail.com','0213','8049640238', 6300)")
        db.execSQL("insert into $TABLE_NAME values('016989','yashi Garg', 'yashiGarg@gmail.com','9652','9495640210', 7800)")
        db.execSQL("insert into $TABLE_NAME values('854610','kritika Shah', 'kritikaShah@gmail.com','1256','7885021539', 1000)")
        db.execSQL("insert into $TABLE_NAME values('789652','Ritika Jain', 'ritikaJain@gmail.com','0258','9109565230', 1500)")
        db.execSQL("insert into $TABLE_NAME values('032136','Rohan Bhudani', 'rohanBhudani@gmail.com','5450','7292591201', 9000)")
        db.execSQL("insert into $TABLE_NAME values('005445','Anurag Kashyap', 'anuragKashyap@gmail.com','9898','9115641245', 19800)")
        db.execSQL("insert into $TABLE_NAME values('002125','Keshav Sharma', 'KeshavSha@gmail.com','2356','9345698712', 15100)")
        db.execSQL("insert into $TABLE_NAME values('045621','Khitij Joshi', 'khitijJoshi@gmail.com','8877','9109541001', 5500)")
        db.execSQL("insert into $TABLE_NAME values('000251','Maqsoor Khan', 'maqsoorKhan@gmail.com','4453','8154642243', 9500)")
        db.execSQL("insert into $TABLE_NAME values('012098','Amandeep Singh', 'amandeepSingh@gmail.com','7193','7893641245', 6000)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME)
            onCreate(db)
        }
    }

    fun readAllData(): Cursor {
        val db = this.writableDatabase
        return db.rawQuery("select * from " + UserEntry.TABLE_NAME, null)
    }

    fun readParticularData(accountNo: String): Cursor {
        val db = this.writableDatabase
        return db.rawQuery(
            "select * from " + UserEntry.TABLE_NAME + " where " +
                    UserEntry.COLUMN_USER_ACCOUNT_NUMBER + " = " + accountNo, null
        )
    }

    fun updateAmount(accountNo: String?, amount: Int) {
        Log.d("TAG", "update Amount")
        val db = this.writableDatabase
        db.execSQL(
            "update " + UserEntry.TABLE_NAME + " set " + UserEntry.COLUMN_USER_ACCOUNT_BALANCE + " = " + amount + " where " +
                    UserEntry.COLUMN_USER_ACCOUNT_NUMBER + " = " + accountNo
        )
    }

    companion object {
        /* Name of the database file */
        private const val DATABASE_NAME = "User.db"

        /*Database version. If you change the database schema, you must increment the database version. */
        private const val DATABASE_VERSION = 1
    }
}