package com.example.bankapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bankapp.R
import kotlinx.android.synthetic.main.activity_finish.*
import kotlinx.android.synthetic.main.activity_history.*

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        //Set up the toolbar
        setSupportActionBar(toolbar_finish_activity)

        val actionbar = supportActionBar

        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "Finish"
        }

        toolbar_finish_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnFinish.setOnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

}