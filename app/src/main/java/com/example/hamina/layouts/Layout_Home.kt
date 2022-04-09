package com.example.hamina.layouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hamina.R
import com.example.hamina.adapters.Adapter_Product
import com.example.hamina.databinding.ActivityLayoutHomeBinding
import com.example.hamina.units.ProductMen
import com.google.firebase.database.*

class Layout_Home : AppCompatActivity() {

    private lateinit var binding: ActivityLayoutHomeBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLayoutHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        Get info user
        val info = intent.getStringExtra("info")
        database = FirebaseDatabase.getInstance().getReference(info.toString())

//        Change another layout
        binding.store.setOnClickListener {

            val intent = Intent(this, Layout_Store::class.java).also {
                it.putExtra("info", info)

                startActivity(it)
                overridePendingTransition(R.anim.slide_blur, R.anim.slide_blur)
            }

        }
        binding.notification.setOnClickListener {

//            val intent = Intent(this, Layout_Notification::class.java).also {
//                it.putExtra("info", info)
//                overridePendingTransition(R.anim.slide_blur, R.anim.slide_blur)
//                startActivity(it)
//            }
            val intent = Intent(this, Layout_Notification::class.java)
            intent.putExtra("info", info)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_blur, R.anim.slide_blur)

        }
    }




}