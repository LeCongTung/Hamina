package com.example.hamina.shows

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hamina.R
import com.example.hamina.adapters.Adapter_Type
import com.example.hamina.layouts.Layout_Home
import com.example.hamina.layouts.Layout_Information
import com.example.hamina.layouts.Layout_Notification
import com.example.hamina.layouts.Layout_Store
import com.example.hamina.units.Type
import com.google.firebase.database.*

class Show_ListType : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var typeArrayList: ArrayList<Type>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list_type)

//      Init
        val btnHome: ImageButton = findViewById(R.id.home)
        val btnStore: Button = findViewById(R.id.store)
        val btnNotification: ImageButton = findViewById(R.id.notification)
        val listItem: RecyclerView = findViewById(R.id.list_item)

        val btnCart: ImageView = findViewById(R.id.btn_cart)
        val btnInformation: ImageView = findViewById(R.id.btn_userprofile)

//        Get info user
        val type = intent.getStringExtra("type").toString()
        val info = intent.getStringExtra("info").toString()
        database = FirebaseDatabase.getInstance().getReference(type.toString())

//        Change another layout
        btnHome.setOnClickListener {

            val intent = Intent(this, Layout_Home::class.java)
            intent.putExtra("info", info)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_blur, R.anim.slide_blur)
        }

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

//        Excute event -- Change to another selection in appBar
        btnCart.setOnClickListener{

            val intent = Intent(this, Show_ListCart::class.java)
            intent.putExtra("info", info)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_blur_right, R.anim.slide_appear_left)
        }

        btnInformation.setOnClickListener{

            val intent = Intent(this, Layout_Information::class.java)
            intent.putExtra("info", info)
            intent.putExtra("type", type)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_back, R.anim.slide_back2)
        }

        listItem.setHasFixedSize(true)
        listItem.layoutManager = GridLayoutManager(this, 2)
        typeArrayList = arrayListOf<Type>()
        getData()
    }

    private fun getData() {

        var tradeadapter = Adapter_Type(typeArrayList)
        val type = intent.getStringExtra("type").toString()
        val info = intent.getStringExtra("info").toString()

        database = FirebaseDatabase.getInstance().getReference(type.toString())

        val listItem: RecyclerView = findViewById(R.id.list_item)
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                typeArrayList.clear()
                if (snapshot.exists()){

                    for (productSnapshot in snapshot.children){

                        val product = productSnapshot.getValue(Type::class.java)
                        typeArrayList.add(product!!)
                    }

                    listItem.adapter = tradeadapter
                    tradeadapter.setOnItemClickListener(object : Adapter_Type.onItemClickListener{

                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@Show_ListType, Show_ListProduct::class.java)
                            intent.putExtra("type", type)
                            intent.putExtra("info", info)
                            intent.putExtra("pertype", typeArrayList[position].name)
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_blur, R.anim.slide_blur)
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}