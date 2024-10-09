package com.demo.assignmentiiscsubmitedbypankajverma

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OnBoardingScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_on_boarding_screen)

//        hide status bar
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

//        splash screen
        Handler(Looper.getMainLooper()).postDelayed({
//            explicit intent
            val intent = Intent(this@OnBoardingScreen,Activityobboard::class.java)
            startActivity(intent)
            finish()
        },3000)//3 sec

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fragmentContainer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}