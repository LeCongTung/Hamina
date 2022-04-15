package com.example.hamina.shows

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hamina.R
import com.example.hamina.activities.Activity_Signin
import com.example.hamina.adapters.Adapter_Product
import com.example.hamina.adapters.Adapter_Type
import com.example.hamina.units.Product
import com.example.hamina.units.Type
import com.google.firebase.database.*

class Layout_ShowProduct : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var productArrayList: ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_show_product)

//      Init
        val btnBack: ImageButton = findViewById(R.id.btn_back)
        val tvTitle: TextView = findViewById(R.id.title)
        val listItem: RecyclerView = findViewById(R.id.list_item)

//        Get info user
        val type = intent.getStringExtra("type").toString()
        val pertype = intent.getStringExtra("pertype").toString()
        val info = intent.getStringExtra("info").toString()


        val nameproduct = type + pertype
        tvTitle.setText(pertype)


        btnBack.setOnClickListener {

            val intent = Intent(this@Layout_ShowProduct, Layout_ShowType::class.java)
            intent.putExtra("type", type)
            intent.putExtra("info", info)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_blur, R.anim.slide_blur)
        }

        listItem.setHasFixedSize(true)
        listItem.layoutManager = GridLayoutManager(this, 2)
        productArrayList = arrayListOf<Product>()
        getData()
    }

    private fun getData() {

        var tradeadapter = Adapter_Product(productArrayList)
        val type = intent.getStringExtra("type").toString()
        val pertype = intent.getStringExtra("pertype").toString()
        val info = intent.getStringExtra("info").toString()

        val nameproduct = type + pertype
        database = FirebaseDatabase.getInstance().getReference(nameproduct)

        val listItem: RecyclerView = findViewById(R.id.list_item)
        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (productSnapshot in snapshot.children){

                        val product = productSnapshot.getValue(Product::class.java)
                        productArrayList.add(product!!)
                    }

                    listItem.adapter = tradeadapter
                    tradeadapter.setOnItemClickListener(object : Adapter_Product.onItemClickListener{

                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@Layout_ShowProduct, Layout_ShowDetail::class.java)
                            intent.putExtra("product", productArrayList[position].name)
                            intent.putExtra("info", info)
                            intent.putExtra("type", type)
                            intent.putExtra("pertype", pertype)
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_blur_right, R.anim.slide_appear_left)
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