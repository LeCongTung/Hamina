package com.example.hamina.shows

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hamina.R
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
        val tvproductmadein: TextView = findViewById(R.id.item_madein)
        val tvproductmaterial: TextView = findViewById(R.id.item_material)
        val imageproduct: ImageView = findViewById(R.id.item_image)

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

                val colormain = it.child("colormain").value.toString().trim()
                val colorsecond = it.child("colorsecond").value.toString().trim()
                val description = it.child("description").value.toString().trim()
                val madein = it.child("madein").value.toString().trim()
                val material = it.child("material").value.toString().trim()
                val price = it.child("price").value.toString().trim()
                val photomain = it.child("photomain").value.toString().trim()

                val getPrice = price.toInt()

                tvproductprice.setText("$" + price)
                tvproductmadein.setText("Made in: " + madein)
                tvproductmaterial.setText(material)
                tvproductdescription.setText(description)
//                Picasso.with(imageproduct.context).load(Product.)

            }else
                Toast.makeText(this, "Your account hasn't existed", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(
                this,
                "Your account hasn't existed",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}