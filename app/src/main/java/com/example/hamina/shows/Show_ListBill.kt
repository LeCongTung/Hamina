package com.example.hamina.shows

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hamina.R
import com.example.hamina.activities.Activity_Abate
import com.example.hamina.adapters.Adapter_Cart
import com.example.hamina.units.Cart
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class Show_ListBill : AppCompatActivity() {

    private lateinit var databaseBill: DatabaseReference
    private lateinit var databaseHistory: DatabaseReference
    private lateinit var cartArrayList: ArrayList<Cart>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list_bill)

//        Init
        val btnBack: ImageButton = findViewById(R.id.btn_back)
        val listItem: RecyclerView = findViewById(R.id.list_item)

//      Get info user
        val info = intent.getStringExtra("info").toString()

//        Excute event -- Back to previous layout
        btnBack.setOnClickListener {

            val intent = Intent(this, Show_ListHistory::class.java)
            intent.putExtra("info", info)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_back, R.anim.slide_back2)
        }

        listItem.setHasFixedSize(true)
        listItem.layoutManager = LinearLayoutManager(this)
        cartArrayList = arrayListOf<Cart>()
        getData()
    }

    private fun getData() {

        val tvhistoryid: TextView = findViewById(R.id.history_id)
        val tvhistorydate: TextView = findViewById(R.id.history_date)
        val tvhistorymoney: TextView = findViewById(R.id.history_total)

        var tradeadapter = Adapter_Cart(cartArrayList)
        val info = intent.getStringExtra("info").toString()
        val id = intent.getStringExtra("id").toString()

        databaseHistory = FirebaseDatabase.getInstance().getReference("History/$info/Bill")
        databaseHistory.child(id).get().addOnSuccessListener {

            if (it.exists()){

                val id = it.child("id").value.toString().trim()
                val date = it.child("date").value.toString().trim()
                val total = it.child("total").value.toString().trim()

                tvhistoryid.setText(id)
                tvhistorydate.setText(date)
                tvhistorymoney.setText(total)
            }
        }

        databaseBill = FirebaseDatabase.getInstance().getReference("History/$info/Detail/$id")
        val listItem: RecyclerView = findViewById(R.id.list_item)
        databaseBill.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                cartArrayList.clear()
                if (snapshot.exists()){

                    for (productSnapshot in snapshot.children){

                        val cart = productSnapshot.getValue(Cart::class.java)
                        cartArrayList.add(cart!!)
                    }

                    listItem.adapter = tradeadapter
                    tradeadapter.setOnItemClickListener(object : Adapter_Cart.onItemClickListener{

                        override fun onItemClick(position: Int) {}
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}