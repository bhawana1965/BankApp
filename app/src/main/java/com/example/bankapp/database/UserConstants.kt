package com.example.bankapp.database

import android.provider.BaseColumns


class UserConstants private constructor() {
    object UserEntry : BaseColumns {
        /* Name of database table  */
        const val TABLE_NAME = "user"

        const val _ID = BaseColumns._ID
        const val COLUMN_USER_NAME = "name"
        const val COLUMN_USER_ACCOUNT_NUMBER = "accountNo"
        const val COLUMN_USER_EMAIL = "email"
        const val COLUMN_USER_IFSC_CODE = "ifscCode"
        const val COLUMN_USER_PHONE_NO = "phoneNo"
        const val COLUMN_USER_ACCOUNT_BALANCE = "balance"
    }
}