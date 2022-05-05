package com.example.hamina.shows

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hamina.R
import com.example.hamina.adapters.Adapter_History
import com.example.hamina.layouts.Layout_Information
import com.example.hamina.units.History
import com.google.firebase.database.*
import kotlin.collections.ArrayList

class Show_ListHistory : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var historyArrayList: ArrayList<History>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list_history)

//        Init
        val btnBack: ImageButton = findViewById(R.id.btn_back)
        val listItem: RecyclerView = findViewById(R.id.list_item)

//      Get info user
        val info = intent.getStringExtra("info").toString()

        btnBack.setOnClickListener {

            val intent = Intent(this, Layout_Information::class.java)
            intent.putExtra("info", info)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_back, R.anim.slide_back2)
        }

        listItem.setHasFixedSize(true)
        listItem.layoutManager = LinearLayoutManager(this)
        historyArrayList = arrayListOf<History>()
        getData()
    }

    private fun getData() {

        var tradeadapter = Adapter_History(historyArrayList)
        val info = intent.getStringExtra("info").toString()

        database = FirebaseDatabase.getInstance().getReference("History/$info/Bill")

        val listItem: RecyclerView = findViewById(R.id.list_item)
        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                historyArrayList.clear()
                if (snapshot.exists()){

                    for (productSnapshot in snapshot.children){

                        val history = productSnapshot.getValue(History::class.java)
                        historyArrayList.add(history!!)

                        historyArrayList.sortByDescending {

                            it.id
                        }
                    }

                    listItem.adapter = tradeadapter
                    tradeadapter.setOnItemClickListener(object : Adapter_History.onItemClickListener{

                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@Show_ListHistory, Show_ListBill::class.java)
                            intent.putExtra("id", historyArrayList[position].id)
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