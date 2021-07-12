package com.example.bankapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.bankapp.R


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        // hide the title bar
        getSupportActionBar()?.hide()

        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_splash_screen)

        //Set Animation for the image
        val backgroundImage: ImageView = findViewById(R.id.imgSplash)
        val slideAnimation = AnimationUtils.loadAnimation(this,R.anim.slide_slide)
        backgroundImage.startAnimation(slideAnimation)

        //Handler used to delay the splash activity for specified period of time
        Handler().postDelayed({
            val mainIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            finish()
            startActivity(mainIntent)
        },3000)
    }
}