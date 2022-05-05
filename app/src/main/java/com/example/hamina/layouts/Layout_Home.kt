package com.example.hamina.layouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hamina.R
import com.example.hamina.adapters.Adapter_Product
import com.example.hamina.databinding.ActivityLayoutHomeBinding
import com.google.firebase.database.*

class Layout_Home : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_home)

//      Init
        val btnStore: ImageButton = findViewById(R.id.store)
        val btnNotification: ImageButton = findViewById(R.id.notification)

//        Get info user
        val info = intent.getStringExtra("info").toString()
//        database = FirebaseDatabase.getInstance().getReference(info.toString())

//        Change another layout
        btnStore.setOnClickListener {

            val intent = Intent(this, Layout_Store::class.java)
            intent.putExtra("info", info)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_blur, R.anim.slide_blur)

        }

        btnNotification.setOnClickListener {

            val intent = Intent(this, Layout_Notification::class.java)
            intent.putExtra("info", info)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_blur, R.anim.slide_blur)
        }
    }




}