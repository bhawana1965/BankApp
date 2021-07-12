package com.example.bankapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.bankapp.R


class MainActivity : AppCompatActivity() {

    lateinit var btnViewCustomer : Button
    lateinit var btnTransaction : Button
    lateinit var btnExit : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnViewCustomer = findViewById(R.id.btnViewAllCustomers)
        btnTransaction = findViewById(R.id.btnTransactionHistory)
        btnExit = findViewById(R.id.btnExit)

        //Animation for buttons
        val bounceAnim = AnimationUtils.loadAnimation(this,R.anim.bounce)

        btnViewCustomer.setOnClickListener {
            btnViewCustomer.startAnimation(bounceAnim)
            var viewIntent = Intent(this@MainActivity, AllCustomerActivity::class.java)
            startActivity(viewIntent)
        }


        btnTransaction.setOnClickListener {
            btnTransaction.startAnimation(bounceAnim)
            var historyIntent = Intent(this@MainActivity, HistoryActivity::class.java)
            startActivity(historyIntent)
        }


        btnExit.setOnClickListener {

            btnExit.startAnimation(bounceAnim)

            AlertDialog.Builder(this@MainActivity)
                .setTitle("Confirmation")
                .setMessage("Are you sure you want to exit the app ")
                .setPositiveButton("Yes"){text,listener->
                    ActivityCompat.finishAffinity(this)
                }
                .setNegativeButton("Cancel"){dialog,_ ->
                    dialog.dismiss()
                }.show()

        }

    }
}