package com.example.bankapp.database

import com.example.bankapp.database.TransactionsConstants.TransactionEntry

import android.content.ContentValues
import android.content.Context

import android.database.sqlite.SQLiteDatabase

import android.database.sqlite.SQLiteOpenHelper


class TransactionsSqliteHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        // Create a String that contains the SQL statement to create the pets table
        val SQL_CREATE_TRANSACTION_TABLE = ("CREATE TABLE " + TransactionEntry.TABLE_NAME + " ("
                + TransactionEntry.COLUMN_FROM_NAME + " VARCHAR, "
                + TransactionEntry.COLUMN_TO_NAME + " VARCHAR, "
                + TransactionEntry.COLUMN_AMOUNT + " INTEGER, "
                + TransactionEntry.COLUMN_STATUS + " INTEGER);")

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_TRANSACTION_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TransactionEntry.TABLE_NAME)
            onCreate(db)
        }
    }

    fun insertTransferData(
        fromName: String?,
        toName: String,
        amount: String?,
        status: Int
    ): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(TransactionEntry.COLUMN_FROM_NAME, fromName)
        contentValues.put(TransactionEntry.COLUMN_TO_NAME, toName)
        contentValues.put(TransactionEntry.COLUMN_AMOUNT, amount)
        contentValues.put(TransactionEntry.COLUMN_STATUS, status)
        val result = db.insert(TransactionEntry.TABLE_NAME, null, contentValues)
        return result != -1L
    }

    companion object {
        /* Name of the database file  */
        private const val DATABASE_NAME = "transaction.db"

        /* Database version. If you change the database schema, you must increment the database version. */
        private const val DATABASE_VERSION = 1
    }
}