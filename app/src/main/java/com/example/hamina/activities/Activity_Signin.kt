package com.example.hamina.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hamina.R
import com.example.hamina.databinding.ActivitySigninBinding
import com.example.hamina.databinding.ActivitySignupBinding

class Activity_Signin : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signup.setOnClickListener {

            startActivity(Intent(this, Activity_Signup::class.java))
        }
    }


}