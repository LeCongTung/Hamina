package com.example.hamina.layouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hamina.R
import com.example.hamina.databinding.ActivityLayoutHomeBinding
import com.example.hamina.databinding.ActivityLayoutNotificationBinding
import com.example.hamina.databinding.ActivityLayoutStoreBinding

class Layout_Notification : AppCompatActivity() {

    private lateinit var binding: ActivityLayoutNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLayoutNotificationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        Get info user
        val info = intent.getStringExtra("info")

//        Change another layout
        binding.store.setOnClickListener {

            val intent = Intent(this, Layout_Store::class.java).also {
                it.putExtra("info", info)
                overridePendingTransition(R.anim.slide_blur, R.anim.slide_blur)
                startActivity(it)
            }
        }
        binding.home.setOnClickListener {

            val intent = Intent(this, Layout_Home::class.java).also {
                it.putExtra("info", info)
                overridePendingTransition(R.anim.slide_blur, R.anim.slide_blur)
                startActivity(it)
            }
        }
    }
}