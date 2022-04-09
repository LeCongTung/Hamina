package com.example.hamina.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hamina.R
import com.example.hamina.databinding.ActivitySigninBinding
import com.example.hamina.databinding.ActivitySignupBinding
import com.example.hamina.layouts.Layout_Home
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Activity_Signin : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("User")

        binding.btnSignup.setOnClickListener {

            startActivity(Intent(this, Activity_Signup::class.java))
        }

        binding.btnSignin.setOnClickListener {

            val phonenumber: String = binding.phonenumber.text.toString().trim()
            val password: String = binding.password.text.toString().trim()

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

                            binding.phonenumber.setText("")
                            binding.password.setText("")

                            val intent = Intent(this, Layout_Home::class.java).also {
                                it.putExtra("info", phonenumber)
                                startActivity(it)
                            }

                        }else{
                            binding.password.setText("")
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