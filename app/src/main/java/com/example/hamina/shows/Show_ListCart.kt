package com.example.hamina.shows

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hamina.R
import com.example.hamina.activities.Activity_Abate
import com.example.hamina.adapters.Adapter_Cart
import com.example.hamina.layouts.Layout_AfterPay
import com.example.hamina.layouts.Layout_Store
import com.example.hamina.units.Cart
import com.example.hamina.units.History
import com.example.hamina.units.User
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class Show_ListCart : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var databaseDelete: DatabaseReference
    private lateinit var databaseTotal: DatabaseReference
    private lateinit var databaseUser: DatabaseReference

    private lateinit var databaseCurrent: DatabaseReference
    private lateinit var databaseBill: DatabaseReference
    private lateinit var cartArrayList: ArrayList<Cart>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list_cart)

//      Init
        val btnBack: ImageButton = findViewById(R.id.btn_back)
        val listItem: RecyclerView = findViewById(R.id.list_item)
        val btnBuy: Button = findViewById(R.id.btn_buy)

        val tvallprice: TextView = findViewById(R.id.allproduct_price)

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
        loadTotal()
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

//        Buy all products you have in cart
        databaseDelete = FirebaseDatabase.getInstance().getReference("Cart/$info")
        databaseBill = FirebaseDatabase.getInstance().getReference("History/$info/Bill")
        databaseCurrent = FirebaseDatabase.getInstance().getReference("History/$info")
        databaseUser = FirebaseDatabase.getInstance().getReference("User")
        btnBuy.setOnClickListener {

//            Add new history
            databaseCurrent.child("currentID").get().addOnSuccessListener {

                if (it.exists()) {

//            Get id for history
                    val cID = it.child("id").value.toString().trim()

//            Get data buy all products in cart
                    val current = LocalDateTime.now()

                    val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                    val formatted = current.format(formatter)

//            Get total
                    val valueTotal = tvallprice.text.toString().trim()
                    val history = History(cID, formatted, valueTotal)

                    databaseUser.child(info).get().addOnSuccessListener {

                        if (it.exists()) {

                            val id = it.child("iduser").value.toString().trim()
                            val phonenumber = it.child("phonenumber").value.toString().trim()
                            val password = it.child("password").value.toString().trim()
                            val firstname = it.child("firstname").value.toString().trim()
                            val lastName = it.child("lastName").value.toString().trim()
                            val usergmail = it.child("usergmail").value.toString().trim()
                            val addressuser = it.child("addressuser").value.toString().trim()
                            val money = it.child("money").value.toString().toInt()
                            val totalused = it.child("totalused").value.toString().toInt()

                            var valueMoney = money
                            var valueTotalUsed = totalused
                            if (valueMoney > valueTotal.toInt()) {
                                valueMoney = money - valueTotal.toInt()
                                valueTotalUsed = totalused + valueTotal.toInt()

                                val user = User(id, phonenumber, password, firstname, lastName, usergmail, addressuser, valueMoney, valueTotalUsed)
                                databaseUser.child(phonenumber).setValue(user).addOnCompleteListener{

                                    if (it.isSuccessful) {
                                    } else { Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show() }
                                }

                                databaseBill.child(cID).setValue(history).addOnCompleteListener{

                                    if (it.isSuccessful) {
                                    } else { Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show() }
                                }

                                databaseDelete.child("List").removeValue().addOnSuccessListener {}
                                databaseDelete.child("total").removeValue().addOnSuccessListener {}
                                databaseCurrent.child("currentID").removeValue().addOnSuccessListener {}

                                val intent = Intent(this, Layout_AfterPay::class.java)
                                intent.putExtra("info", info)
                                startActivity(intent)
                                overridePendingTransition(R.anim.slide_blur, R.anim.slide_blur)
                            }else{

                                Toast.makeText(this, "You don't have enough money to buy", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }


        }
    }

    private fun getData() {

        var tradeadapter = Adapter_Cart(cartArrayList)
        val info = intent.getStringExtra("info").toString()

        database = FirebaseDatabase.getInstance().getReference("Cart/$info/List")

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
                    loadTotal()
                    tradeadapter.setOnItemClickListener(object : Adapter_Cart.onItemClickListener{

                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@Show_ListCart, Activity_Abate::class.java)
                            intent.putExtra("product", cartArrayList[position].name)
                            intent.putExtra("size", cartArrayList[position].size)
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

    private fun getDataDecrease() {

        var tradeadapter = Adapter_Cart(cartArrayList)
        val info = intent.getStringExtra("info").toString()

        database = FirebaseDatabase.getInstance().getReference("Cart/$info/List")

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

                            val intent = Intent(this@Show_ListCart, Activity_Abate::class.java)
                            intent.putExtra("product", cartArrayList[position].name)
                            intent.putExtra("size", cartArrayList[position].size)
                            intent.putExtra("info", info)
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_blur_right, R.anim.slide_appear_left)
                        }
                    })
                }
                loadTotal()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun getDataAtoZ() {

        var tradeadapter = Adapter_Cart(cartArrayList)
        val info = intent.getStringExtra("info").toString()

        database = FirebaseDatabase.getInstance().getReference("Cart/$info/List")

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

                            val intent = Intent(this@Show_ListCart, Activity_Abate::class.java)
                            intent.putExtra("product", cartArrayList[position].name)
                            intent.putExtra("size", cartArrayList[position].size)
                            intent.putExtra("info", info)
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_blur_right, R.anim.slide_appear_left)
                        }
                    })
                }
                loadTotal()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun getDataZtoA() {

        var tradeadapter = Adapter_Cart(cartArrayList)
        val info = intent.getStringExtra("info").toString()

        database = FirebaseDatabase.getInstance().getReference("Cart/$info/List")

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

                            val intent = Intent(this@Show_ListCart, Activity_Abate::class.java)
                            intent.putExtra("product", cartArrayList[position].name)
                            intent.putExtra("size", cartArrayList[position].size)
                            intent.putExtra("info", info)
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_blur_right, R.anim.slide_appear_left)
                        }
                    })
                }
                loadTotal()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun loadTotal(){

        val info = intent.getStringExtra("info").toString()
        val tvtotalprice: TextView = findViewById(R.id.allproduct_price)
        databaseTotal = FirebaseDatabase.getInstance().getReference("Cart/$info")
        databaseTotal.child("total").get().addOnSuccessListener {

            if (it.exists()) {

                val availableValue = it.child("total").value.toString()
                tvtotalprice.setText(availableValue)
            }
        }
    }
}