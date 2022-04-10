package com.example.hamina.shows

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hamina.R
import com.example.hamina.adapters.Adapter_Type
import com.example.hamina.layouts.Layout_Home
import com.example.hamina.layouts.Layout_Notification
import com.example.hamina.units.Type
import com.google.firebase.database.*

class Layout_ShowType : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var productArrayList: ArrayList<Type>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_show_type)

        //      Init
        val btnHome: ImageButton = findViewById(R.id.home)
        val btnNotification: ImageButton = findViewById(R.id.notification)
        val listItem: RecyclerView = findViewById(R.id.list_item)

        //        Get info user
        val info = intent.getStringExtra("info")
        val type = intent.getStringExtra("type")
        database = FirebaseDatabase.getInstance().getReference(type.toString())

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


        listItem.setHasFixedSize(true)
        listItem.layoutManager = GridLayoutManager(this, 2)
        productArrayList = arrayListOf<Type>()
        getData()


    }

    private fun getData() {

        val listItem: RecyclerView = findViewById(R.id.list_item)
        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (productSnapshot in snapshot.children){

                        val product = productSnapshot.getValue(Type::class.java)
                        productArrayList.add(product!!)
                    }

                    listItem.adapter = Adapter_Type(productArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}