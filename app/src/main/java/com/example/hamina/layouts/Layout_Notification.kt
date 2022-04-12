package com.example.hamina.layouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.hamina.R
import com.example.hamina.databinding.ActivityLayoutHomeBinding
import com.example.hamina.databinding.ActivityLayoutNotificationBinding
import com.example.hamina.databinding.ActivityLayoutStoreBinding

class Layout_Notification : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_notification)

//      Init
        val btnStore: ImageButton = findViewById(R.id.store)
        val btnHome: ImageButton = findViewById(R.id.home)

//        Get info user
        val info = intent.getStringExtra("info").toString()

//        Change another layout
        btnStore.setOnClickListener {

            val intent = Intent(this, Layout_Store::class.java)
            intent.putExtra("info", info)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_blur, R.anim.slide_blur)
        }

        btnHome.setOnClickListener {

            val intent = Intent(this, Layout_Home::class.java)
            intent.putExtra("info", info)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_blur, R.anim.slide_blur)
        }
    }
}