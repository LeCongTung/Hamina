package com.example.hamina.layouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hamina.R
import com.example.hamina.activities.Activity_Signin
import com.example.hamina.activities.Activity_Signup
import com.example.hamina.databinding.ActivityLayoutGettingStartBinding

class Layout_GettingStart : AppCompatActivity() {

    private lateinit var binding: ActivityLayoutGettingStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLayoutGettingStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener {

            startActivity(Intent(this, Activity_Signup::class.java))
        }

        binding.btnSignin.setOnClickListener {

            startActivity(Intent(this, Activity_Signin::class.java))
        }
    }
}