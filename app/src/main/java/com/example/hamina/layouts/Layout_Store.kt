package com.example.hamina.layouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import com.example.hamina.R
import com.example.hamina.shows.Layout_ShowType
import com.google.firebase.database.*

class Layout_Store : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_store)

//      Init
        val btnHome: ImageButton = findViewById(R.id.home)
        val btnNotification: ImageButton = findViewById(R.id.notification)
        val btnChoiceMen: CardView = findViewById(R.id.choiceMen)
        val btnChoiceWomen: CardView = findViewById(R.id.choiceWomen)
        val btnChoiceJewelry: CardView = findViewById(R.id.choiceJewelry)

//        Get info user
        val info = intent.getStringExtra("info").toString()

//        Change another layout
        btnHome.setOnClickListener {

            val intent = Intent(this, Layout_Home::class.java)
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

//        Choice type to buy clothes
        btnChoiceMen.setOnClickListener {

            val type = "ProductMen"
            val intent = Intent(this, Layout_ShowType::class.java)
            intent.putExtra("info", info)
            intent.putExtra("type", type)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_blur, R.anim.slide_blur)
        }

        btnChoiceWomen.setOnClickListener {

            val type = "ProductWomen"
            val intent = Intent(this, Layout_ShowType::class.java)
            intent.putExtra("info", info)
            intent.putExtra("type", type)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_blur, R.anim.slide_blur)
        }

        btnChoiceJewelry.setOnClickListener {

            val type = "Jewelry"
            val intent = Intent(this, Layout_ShowType::class.java)
            intent.putExtra("info", info)
            intent.putExtra("type", type)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_blur, R.anim.slide_blur)
        }
    }
}