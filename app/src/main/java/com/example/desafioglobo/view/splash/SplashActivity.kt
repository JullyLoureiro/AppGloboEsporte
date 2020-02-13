package com.example.desafioglobo.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.example.desafioglobo.MainActivity
import com.example.desafioglobo.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val SPLASH_TIME_OUT = 2500
        val homeIntent = Intent(this@SplashActivity, MainActivity::class.java)

        val animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        splash.startAnimation(animation)

        Handler().postDelayed({
            startActivity(homeIntent)
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }
}
