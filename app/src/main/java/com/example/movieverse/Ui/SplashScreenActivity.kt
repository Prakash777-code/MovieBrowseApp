package com.example.movieverse.Ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.movieverse.R
import com.example.movieverse.Utils.AppConstants

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        window.statusBarColor = Color.BLACK
        window.navigationBarColor = Color.BLACK

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, AppConstants.MAGIC_SCREEN.toLong())
    }
}