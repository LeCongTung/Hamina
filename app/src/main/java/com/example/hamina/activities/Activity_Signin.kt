package com.example.hamina.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.hamina.R
import com.example.hamina.layouts.Layout_Home
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Activity_Signin : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

//      Init
        val btnSignup: TextView = findViewById(R.id.btn_signup)
        val btnForgetpassword: TextView = findViewById(R.id.btn_forgotpass)
        val etPhonenumber: EditText = findViewById(R.id.phonenumber)
        val etPassword: EditText = findViewById(R.id.password)
        val btnSignin: Button = findViewById(R.id.btn_signin)

        database = FirebaseDatabase.getInstance().getReference("User")

        btnSignup.setOnClickListener {

            startActivity(Intent(this, Activity_Signup::class.java))
        }

        btnSignin.setOnClickListener {

            val phonenumber: String = etPhonenumber.text.toString().trim()
            val password: String = etPassword.text.toString().trim()

            if (phonenumber.equals(""))
                Toast.makeText(
                    this,
                    "Your account hasn't existed",
                    Toast.LENGTH_SHORT
                ).show()
            else{
                database.child(phonenumber).get().addOnSuccessListener {

                    if (it.exists()){

                        val checkphonenumber = it.child("phonenumber").value.toString().trim()
                        val checkpassword = it.child("password").value.toString().trim()

                        if (checkphonenumber.equals(phonenumber) && checkpassword.equals(password)){

                            etPhonenumber.setText("")
                            etPassword.setText("")

                            val intent = Intent(this, Layout_Home::class.java).also {
                                it.putExtra("info", phonenumber)
                                startActivity(it)
                            }

                        }else{
                            etPassword.setText("")
                            Toast.makeText(
                                this,
                                "Password is wrong!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }else
                        Toast.makeText(this, "Your account hasn't existed", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(
                        this,
                        "Your account hasn't existed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}