package com.example.hamina.layouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.hamina.R
import com.example.hamina.activities.Activity_Signin
import com.example.hamina.activities.Activity_Signup
import com.example.hamina.databinding.ActivityLayoutGettingStartBinding

class Layout_GettingStart : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_getting_start)

//      Init
        val btnSignin: Button = findViewById(R.id.btn_signin)
        val btnSignup: TextView = findViewById(R.id.btn_signup)

        btnSignup.setOnClickListener {

            startActivity(Intent(this, Activity_Signup::class.java))
        }

        btnSignin.setOnClickListener {

            startActivity(Intent(this, Activity_Signin::class.java))
        }
    }
}