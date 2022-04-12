package com.example.hamina.shows

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.hamina.R
import com.google.firebase.database.DatabaseReference

class Layout_ShowDetail : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_show_detail)

////      Init
//        val btnHome: ImageButton = findViewById(R.id.home)
//        val btnNotification: ImageButton = findViewById(R.id.notification)
//        val listItem: RecyclerView = findViewById(R.id.list_item)

        //        Get info user
        val type = intent.getStringExtra("type").toString()
        val pertype = intent.getStringExtra("pertype").toString()
        val info = intent.getStringExtra("info").toString()

    }
}