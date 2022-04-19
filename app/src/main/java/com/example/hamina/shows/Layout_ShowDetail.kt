package com.example.hamina.shows

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.hamina.R
import com.example.hamina.activities.Activity_BuyProduct
import com.example.hamina.layouts.Layout_Home
import com.example.hamina.units.Product
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class Layout_ShowDetail : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_show_detail)

//      Init
        val tvproductname: TextView = findViewById(R.id.item_name)
        val tvproductprice: TextView = findViewById(R.id.item_price)
        val tvproductdescription: TextView = findViewById(R.id.item_description)

        val imageMain: ImageView = findViewById(R.id.imageMain)
        val imageBehind: ImageView = findViewById(R.id.imageBehind)
        val imageDetail: ImageView = findViewById(R.id.imageDetail)
        val imageModel: ImageView = findViewById(R.id.imageModel)

        val btnBack: ImageButton = findViewById(R.id.btn_back)
        val btnDetail: Button = findViewById(R.id.btn_detail)

        //        Get info user
        val type = intent.getStringExtra("type").toString()
        val pertype = intent.getStringExtra("pertype").toString()
        val product = intent.getStringExtra("product").toString()
        val info = intent.getStringExtra("info").toString()


        val nameproduct = type + pertype
        database = FirebaseDatabase.getInstance().getReference(nameproduct)

        tvproductname.setText(product)
        database.child(product).get().addOnSuccessListener{

            if (it.exists()){

                val description = it.child("description").value.toString().trim()
                val price = it.child("price").value.toString().trim()
                val photomain = it.child("photomain").value.toString().trim()
                val photobehind = it.child("photobehind").value.toString().trim()
                val photodetail = it.child("photodetail").value.toString().trim()
                val photomodel = it.child("photomodel").value.toString().trim()

                tvproductprice.setText("$" + price)
                tvproductdescription.setText(description)
                Picasso.with(imageMain.context).load(photomain).into(imageMain)
                Picasso.with(imageBehind.context).load(photobehind).into(imageBehind)
                Picasso.with(imageDetail.context).load(photodetail).into(imageDetail)
                Picasso.with(imageModel.context).load(photomodel).into(imageModel)


            }else
                Toast.makeText(this, "This item hasn't existed", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(
                this,
                "Fail to connect!",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnBack.setOnClickListener {

            val intent = Intent(this, Layout_ShowProduct::class.java)
            intent.putExtra("type", type)
            intent.putExtra("pertype", pertype)
            intent.putExtra("info", info)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_blur, R.anim.slide_blur)
        }

        btnDetail.setOnClickListener {

            val intent = Intent(this, Activity_BuyProduct::class.java)
            intent.putExtra("type", type)
            intent.putExtra("pertype", pertype)
            intent.putExtra("product", product)
            intent.putExtra("info", info)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_blur, R.anim.slide_blur)
        }
    }
}