package com.example.hamina.shows

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hamina.R
import com.example.hamina.activities.Activity_Abate
import com.example.hamina.adapters.Adapter_Cart
import com.example.hamina.adapters.Adapter_Favourite
import com.example.hamina.layouts.Layout_Information
import com.example.hamina.layouts.Layout_Store
import com.example.hamina.units.Cart
import com.example.hamina.units.Favourite
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class Show_ListFavourite : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var favouriteArrayList: ArrayList<Favourite>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list_favourite)

        //      Init
        val btnBack: ImageButton = findViewById(R.id.btn_back)
        val listItem: RecyclerView = findViewById(R.id.list_item)

        val info = intent.getStringExtra("info").toString()

        btnBack.setOnClickListener {

            val intent = Intent(this, Layout_Information::class.java)
            intent.putExtra("info", info)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_back, R.anim.slide_back2)
        }

        listItem.setHasFixedSize(true)
        listItem.layoutManager = LinearLayoutManager(this)
        favouriteArrayList = arrayListOf<Favourite>()
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

        var tradeadapter = Adapter_Favourite(favouriteArrayList)
        val info = intent.getStringExtra("info").toString()

        database = FirebaseDatabase.getInstance().getReference("Favourite/$info")

        val listItem: RecyclerView = findViewById(R.id.list_item)
        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                favouriteArrayList.clear()
                if (snapshot.exists()){

                    for (productSnapshot in snapshot.children){

                        val favourite = productSnapshot.getValue(Favourite::class.java)
                        favouriteArrayList.add(favourite!!)

                        favouriteArrayList.sortByDescending {

                            it.price
                        }
                        Collections.reverse(favouriteArrayList)
                    }

                    listItem.adapter = tradeadapter
                    tradeadapter.setOnItemClickListener(object : Adapter_Favourite.onItemClickListener{

                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@Show_ListFavourite, Show_Detail::class.java)
                            intent.putExtra("product", favouriteArrayList[position].name)
                            intent.putExtra("type", favouriteArrayList[position].type)
                            intent.putExtra("pertype", favouriteArrayList[position].pertype)
                            intent.putExtra("info", info)
                            intent.putExtra("from", "fromFavourite")
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

    private fun getDataDecrease() {

        var tradeadapter = Adapter_Favourite(favouriteArrayList)
        val info = intent.getStringExtra("info").toString()

        database = FirebaseDatabase.getInstance().getReference("Favourite/$info")

        val listItem: RecyclerView = findViewById(R.id.list_item)
        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                favouriteArrayList.clear()
                if (snapshot.exists()){

                    for (productSnapshot in snapshot.children){

                        val favourite = productSnapshot.getValue(Favourite::class.java)
                        favouriteArrayList.add(favourite!!)

                        favouriteArrayList.sortByDescending {

                            it.price
                        }
                    }

                    listItem.adapter = tradeadapter
                    tradeadapter.setOnItemClickListener(object : Adapter_Favourite.onItemClickListener{

                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@Show_ListFavourite, Show_Detail::class.java)
                            intent.putExtra("product", favouriteArrayList[position].name)
                            intent.putExtra("type", favouriteArrayList[position].type)
                            intent.putExtra("pertype", favouriteArrayList[position].pertype)
                            intent.putExtra("info", info)
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

    private fun getDataAtoZ() {

        var tradeadapter = Adapter_Favourite(favouriteArrayList)
        val info = intent.getStringExtra("info").toString()

        database = FirebaseDatabase.getInstance().getReference("Favourite/$info")

        val listItem: RecyclerView = findViewById(R.id.list_item)
        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                favouriteArrayList.clear()
                if (snapshot.exists()){

                    for (productSnapshot in snapshot.children){

                        val favourite = productSnapshot.getValue(Favourite::class.java)
                        favouriteArrayList.add(favourite!!)

                        favouriteArrayList.sortByDescending {

                            it.name
                        }
                        Collections.reverse(favouriteArrayList)
                    }

                    listItem.adapter = tradeadapter
                    tradeadapter.setOnItemClickListener(object : Adapter_Favourite.onItemClickListener{

                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@Show_ListFavourite, Show_Detail::class.java)
                            intent.putExtra("product", favouriteArrayList[position].name)
                            intent.putExtra("type", favouriteArrayList[position].type)
                            intent.putExtra("pertype", favouriteArrayList[position].pertype)
                            intent.putExtra("info", info)
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

    private fun getDataZtoA() {

        var tradeadapter = Adapter_Favourite(favouriteArrayList)
        val info = intent.getStringExtra("info").toString()

        database = FirebaseDatabase.getInstance().getReference("Favourite/$info")

        val listItem: RecyclerView = findViewById(R.id.list_item)
        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                favouriteArrayList.clear()
                if (snapshot.exists()){

                    for (productSnapshot in snapshot.children){

                        val favourite = productSnapshot.getValue(Favourite::class.java)
                        favouriteArrayList.add(favourite!!)

                        favouriteArrayList.sortByDescending {

                            it.name
                        }
                    }

                    listItem.adapter = tradeadapter
                    tradeadapter.setOnItemClickListener(object : Adapter_Favourite.onItemClickListener{

                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@Show_ListFavourite, Show_Detail::class.java)
                            intent.putExtra("product", favouriteArrayList[position].name)
                            intent.putExtra("type", favouriteArrayList[position].type)
                            intent.putExtra("pertype", favouriteArrayList[position].pertype)
                            intent.putExtra("info", info)
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