package com.example.hamina.layouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.hamina.R
import com.example.hamina.activities.Activity_Signin

class Layout_GoodBye : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_good_bye)

        var c = Runnable {

            val intent = Intent(this, Activity_Signin::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_blur, R.anim.slide_blur)
        }

        var handler = Handler()
        handler.postDelayed(c, 3000)
    }
}