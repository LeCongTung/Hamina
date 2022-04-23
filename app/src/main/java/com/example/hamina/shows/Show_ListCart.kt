package com.example.hamina.shows

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hamina.R
import com.example.hamina.adapters.Adapter_Cart
import com.example.hamina.adapters.Adapter_Product
import com.example.hamina.layouts.Layout_Store
import com.example.hamina.units.Cart
import com.example.hamina.units.Product
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class Show_ListCart : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var cartArrayList: ArrayList<Cart>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list_cart)

//      Init
        val btnBack: ImageButton = findViewById(R.id.btn_back)
        val listItem: RecyclerView = findViewById(R.id.list_item)

//      Get info user
        val info = intent.getStringExtra("info").toString()

        btnBack.setOnClickListener {

            val intent = Intent(this, Layout_Store::class.java)
            intent.putExtra("info", info)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_back, R.anim.slide_back2)
        }

        listItem.setHasFixedSize(true)
        listItem.layoutManager = LinearLayoutManager(this)
        cartArrayList = arrayListOf<Cart>()
        getData()

        //            Sort a optional price
        val btnSortPrice: ImageView = findViewById(R.id.btn_sortprice)
        var valueSortPrice = "increase"
        btnSortPrice.setOnClickListener {

            if (valueSortPrice.equals("Descrease")){

                valueSortPrice = "Increase"
                btnSortPrice.setImageResource(R.drawable.ic_price_increase_black)

                getData()

            }else{

                valueSortPrice = "Descrease"
                btnSortPrice.setImageResource(R.drawable.ic_price_decrease_black)

                getDataDecrease()
            }
        }

        //            Sort a optional from A (or Z)
        val btnSortAZ: ImageView = findViewById(R.id.btn_sortletter)
        var valueSortAZ = "FromA"
        btnSortAZ.setOnClickListener {

            if (valueSortAZ.equals("FromZ")){

                valueSortAZ = "FromA"
                btnSortAZ.setImageResource(R.drawable.ic_ztoa_black)

                getDataZtoA()

            }else{

                valueSortAZ = "FromZ"
                btnSortAZ.setImageResource(R.drawable.ic_atoz_black)

                getDataAtoZ()
            }
        }
    }

    private fun getData() {

        var tradeadapter = Adapter_Cart(cartArrayList)
        val info = intent.getStringExtra("info").toString()

        database = FirebaseDatabase.getInstance().getReference("Cart/$info")

        val listItem: RecyclerView = findViewById(R.id.list_item)
        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                cartArrayList.clear()
                if (snapshot.exists()){

                    for (productSnapshot in snapshot.children){

                        val cart = productSnapshot.getValue(Cart::class.java)
                        cartArrayList.add(cart!!)

                        cartArrayList.sortByDescending {

                            it.total
                        }
                        Collections.reverse(cartArrayList)
                    }

                    listItem.adapter = tradeadapter
                    tradeadapter.setOnItemClickListener(object : Adapter_Cart.onItemClickListener{

                        override fun onItemClick(position: Int) {

//                            val intent = Intent(this@Show_ListProduct, Show_Detail::class.java)
//                            intent.putExtra("product", productArrayList[position].name)
//                            intent.putExtra("info", info)
//                            intent.putExtra("type", type)
//                            intent.putExtra("pertype", pertype)
//                            startActivity(intent)
//                            overridePendingTransition(R.anim.slide_blur_right, R.anim.slide_appear_left)
                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun getDataDecrease() {

        var tradeadapter = Adapter_Cart(cartArrayList)
        val info = intent.getStringExtra("info").toString()

        database = FirebaseDatabase.getInstance().getReference("Cart/$info")

        val listItem: RecyclerView = findViewById(R.id.list_item)
        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                cartArrayList.clear()
                if (snapshot.exists()){

                    for (productSnapshot in snapshot.children){

                        val cart = productSnapshot.getValue(Cart::class.java)
                        cartArrayList.add(cart!!)

                        cartArrayList.sortByDescending {

                            it.total
                        }
                    }

                    listItem.adapter = tradeadapter
                    tradeadapter.setOnItemClickListener(object : Adapter_Cart.onItemClickListener{

                        override fun onItemClick(position: Int) {

//                            val intent = Intent(this@Show_ListProduct, Show_Detail::class.java)
//                            intent.putExtra("product", productArrayList[position].name)
//                            intent.putExtra("info", info)
//                            intent.putExtra("type", type)
//                            intent.putExtra("pertype", pertype)
//                            startActivity(intent)
//                            overridePendingTransition(R.anim.slide_blur_right, R.anim.slide_appear_left)
                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun getDataAtoZ() {

        var tradeadapter = Adapter_Cart(cartArrayList)
        val info = intent.getStringExtra("info").toString()

        database = FirebaseDatabase.getInstance().getReference("Cart/$info")

        val listItem: RecyclerView = findViewById(R.id.list_item)
        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                cartArrayList.clear()
                if (snapshot.exists()){

                    for (productSnapshot in snapshot.children){

                        val cart = productSnapshot.getValue(Cart::class.java)
                        cartArrayList.add(cart!!)

                        cartArrayList.sortByDescending {

                            it.name
                        }
                        Collections.reverse(cartArrayList)
                    }

                    listItem.adapter = tradeadapter
                    tradeadapter.setOnItemClickListener(object : Adapter_Cart.onItemClickListener{

                        override fun onItemClick(position: Int) {

//                            val intent = Intent(this@Show_ListProduct, Show_Detail::class.java)
//                            intent.putExtra("product", productArrayList[position].name)
//                            intent.putExtra("info", info)
//                            intent.putExtra("type", type)
//                            intent.putExtra("pertype", pertype)
//                            startActivity(intent)
//                            overridePendingTransition(R.anim.slide_blur_right, R.anim.slide_appear_left)
                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun getDataZtoA() {

        var tradeadapter = Adapter_Cart(cartArrayList)
        val info = intent.getStringExtra("info").toString()

        database = FirebaseDatabase.getInstance().getReference("Cart/$info")

        val listItem: RecyclerView = findViewById(R.id.list_item)
        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                cartArrayList.clear()
                if (snapshot.exists()){

                    for (productSnapshot in snapshot.children){

                        val cart = productSnapshot.getValue(Cart::class.java)
                        cartArrayList.add(cart!!)

                        cartArrayList.sortByDescending {

                            it.name
                        }
                    }

                    listItem.adapter = tradeadapter
                    tradeadapter.setOnItemClickListener(object : Adapter_Cart.onItemClickListener{

                        override fun onItemClick(position: Int) {

//                            val intent = Intent(this@Show_ListProduct, Show_Detail::class.java)
//                            intent.putExtra("product", productArrayList[position].name)
//                            intent.putExtra("info", info)
//                            intent.putExtra("type", type)
//                            intent.putExtra("pertype", pertype)
//                            startActivity(intent)
//                            overridePendingTransition(R.anim.slide_blur_right, R.anim.slide_appear_left)
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