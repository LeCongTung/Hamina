package com.example.hamina.shows

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.hamina.R
import com.example.hamina.activities.Activity_BuyProduct
import com.example.hamina.units.Favourite
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class Show_Detail : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var databaseFavourite: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_detail)

//      Init
        val tvproductname: TextView = findViewById(R.id.item_name)
        val tvproductprice: TextView = findViewById(R.id.item_price)
        val tvproductdescription: TextView = findViewById(R.id.item_description)
        val linkImage: TextView = findViewById(R.id.link_photomain)

        val imageMain: ImageView = findViewById(R.id.imageMain)
        val imageBehind: ImageView = findViewById(R.id.imageBehind)
        val imageDetail: ImageView = findViewById(R.id.imageDetail)
        val imageModel: ImageView = findViewById(R.id.imageModel)

        val btnBack: ImageButton = findViewById(R.id.btn_back)
        val btnDetail: Button = findViewById(R.id.btn_detail)
        val btnFavourite: ImageButton = findViewById(R.id.btn_addFavourite)

        //        Get info user
        val type = intent.getStringExtra("type").toString()
        val pertype = intent.getStringExtra("pertype").toString()
        val product = intent.getStringExtra("product").toString()
        val info = intent.getStringExtra("info").toString()


        val nameproduct = type + pertype
        database = FirebaseDatabase.getInstance().getReference(nameproduct)
        databaseFavourite = FirebaseDatabase.getInstance().getReference("Favourite/$info")

        tvproductname.setText(product)
        database.child(product).get().addOnSuccessListener {

            if (it.exists()) {

                val description = it.child("description").value.toString().trim()
                val price = it.child("price").value.toString().trim()
                val photomain = it.child("photomain").value.toString().trim()
                val photobehind = it.child("photobehind").value.toString().trim()
                val photodetail = it.child("photodetail").value.toString().trim()
                val photomodel = it.child("photomodel").value.toString().trim()

                tvproductprice.setText(price)
                tvproductdescription.setText(description)
                linkImage.setText(photomain)
                Picasso.with(imageMain.context).load(photomain).into(imageMain)
                Picasso.with(imageBehind.context).load(photobehind).into(imageBehind)
                Picasso.with(imageDetail.context).load(photodetail).into(imageDetail)
                Picasso.with(imageModel.context).load(photomodel).into(imageModel)

            } else
                Toast.makeText(this, "This item hasn't existed", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(
                this,
                "Fail to connect!",
                Toast.LENGTH_SHORT
            ).show()
        }

        databaseFavourite.child(product).get().addOnSuccessListener {

            if (it.exists()) {

                btnFavourite.setImageResource(R.drawable.ic_favourite_click_white)
                btnFavourite.setBackgroundResource(R.drawable.bg_btn_black)
            }
        }

        btnBack.setOnClickListener {

            val intent = Intent(this, Show_ListProduct::class.java)
            intent.putExtra("type", type)
            intent.putExtra("pertype", pertype)
            intent.putExtra("info", info)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_back, R.anim.slide_back2)
        }

        btnDetail.setOnClickListener {

            val intent = Intent(this, Activity_BuyProduct::class.java)
            intent.putExtra("type", type)
            intent.putExtra("pertype", pertype)
            intent.putExtra("product", product)
            intent.putExtra("info", info)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_blur_right, R.anim.slide_appear_left)
        }

        btnFavourite.setOnClickListener {

            val description = tvproductdescription.text.toString()
            val price = tvproductprice.text.toString().toInt()
            val linkimage = linkImage.text.toString().trim()

            val favourite = Favourite(product, description, linkimage, nameproduct, price)

            databaseFavourite.child(product).get().addOnSuccessListener {

                if (it.exists()) {
                    databaseFavourite.child(product).removeValue().addOnSuccessListener {

                        Toast.makeText(this, "This product has been deleted in your favourite", Toast.LENGTH_SHORT).show()
                        btnFavourite.setImageResource(R.drawable.ic_favorite_black)
                        btnFavourite.setBackgroundResource(R.drawable.bg_btn_choice_black)
                    }

                } else {
                    databaseFavourite.child(product).setValue(favourite).addOnCompleteListener {
                        if (it.isSuccessful) {

                            Toast.makeText(
                                this,
                                "Add to your favourite successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            btnFavourite.setImageResource(R.drawable.ic_favourite_click_white)
                            btnFavourite.setBackgroundResource(R.drawable.bg_btn_black)
                        } else {

                            Toast.makeText(
                                this,
                                "Failed to add to your favourite",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(
                            this,
                            "Fail to connect!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(
                    this,
                    "Fail to connect!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}