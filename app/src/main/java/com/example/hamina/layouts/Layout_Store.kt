package com.example.hamina.layouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hamina.R
import com.example.hamina.adapters.Adapter_Product
import com.example.hamina.databinding.ActivityLayoutHomeBinding
import com.example.hamina.databinding.ActivityLayoutStoreBinding
import com.example.hamina.units.ProductMen
import com.google.firebase.database.*

class Layout_Store : AppCompatActivity() {

    private lateinit var binding: ActivityLayoutStoreBinding
    private lateinit var database: DatabaseReference
    private lateinit var productArrayList: ArrayList<ProductMen>
    private lateinit var productAdapter: Adapter_Product

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLayoutStoreBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //        Get info user
        val info = intent.getStringExtra("info")
//        database = FirebaseDatabase.getInstance().getReference(info.toString())
        database = FirebaseDatabase.getInstance().getReference("ProductMen")

//        Change another layout
        binding.home.setOnClickListener {

            val intent = Intent(this, Layout_Home::class.java).also {
                it.putExtra("info", info)
                overridePendingTransition(R.anim.slide_blur, R.anim.slide_blur)
                startActivity(it)
            }
        }
        binding.notification.setOnClickListener {

            val intent = Intent(this, Layout_Notification::class.java).also {
                it.putExtra("info", info)
                overridePendingTransition(R.anim.slide_blur, R.anim.slide_blur)
                startActivity(it)
            }
        }


        binding.listItem.setHasFixedSize(true)
        binding.listItem.layoutManager = GridLayoutManager(this, 2)
        productArrayList = arrayListOf<ProductMen>()
        getData()


    }

    private fun getData() {

        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (productSnapshot in snapshot.children){

                        val product = productSnapshot.getValue(ProductMen::class.java)
                        productArrayList.add(product!!)
                    }

                    binding.listItem.adapter = Adapter_Product(productArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }
}