package com.example.hamina.layouts

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.hamina.R
import com.example.hamina.activities.Activity_Abate
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class Layout_AfterPay : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_after_pay)

//       Init
        val btnComplete: Button = findViewById(R.id.btn_complete)
        val tvmessenger: TextView = findViewById(R.id.messenger)

        //      Get info user
        val info = intent.getStringExtra("info").toString()

        database = FirebaseDatabase.getInstance().getReference("User")
        database.child(info).get().addOnSuccessListener {

            if (it.exists()) {

                val lastname = it.child("lastName").value.toString().trim()
                tvmessenger.setText("Thank you for buying Hamina's products, " + lastname + "!")
            }
        }

//        Excute event -- Click to go to layout store
        btnComplete.setOnClickListener {

            val intent = Intent(this@Layout_AfterPay, Layout_Store::class.java)
            intent.putExtra("info", info)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_blur, R.anim.slide_blur)
        }
    }
}