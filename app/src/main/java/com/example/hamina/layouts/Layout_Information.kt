package com.example.hamina.layouts

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.hamina.R
import com.example.hamina.activities.Activity_AddMoney
import com.example.hamina.shows.Show_ListCart
import com.example.hamina.shows.Show_ListFavourite
import com.example.hamina.shows.Show_ListHistory
import com.example.hamina.shows.Show_ListType
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class Layout_Information : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var storage: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_information)

        val btnBack: ImageButton = findViewById(R.id.btn_back)

        val btnCharging: TextView = findViewById(R.id.btn_addMoney)
        val btnFavourite: TextView = findViewById(R.id.btn_toFavourite)
        val btnHistory: TextView = findViewById(R.id.btn_toHistory)
        val btnLogout: TextView = findViewById(R.id.btn_logOut)

        val tvusername: TextView = findViewById(R.id.user_name)
        val tvusermoney: TextView = findViewById(R.id.user_cih)
        val viewuserimage: ImageView = findViewById(R.id.profile_image)

        //      Get info user
        val info = intent.getStringExtra("info").toString()

//        Excute all buttons in layout
        btnBack.setOnClickListener {

            val intent = Intent(this, Layout_Store::class.java)
            intent.putExtra("info", info)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_blur_right, R.anim.slide_appear_left)
        }

//        Excute event
        btnCharging.setOnClickListener {

            val intent = Intent(this, Activity_AddMoney::class.java)
            intent.putExtra("info", info)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_blur_right, R.anim.slide_appear_left)
        }

        btnFavourite.setOnClickListener {

            val intent = Intent(this, Show_ListFavourite::class.java)
            intent.putExtra("info", info)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_blur_right, R.anim.slide_appear_left)
        }

        btnHistory.setOnClickListener {

            val intent = Intent(this, Show_ListHistory::class.java)
            intent.putExtra("info", info)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_blur_right, R.anim.slide_appear_left)
        }

        btnLogout.setOnClickListener {

            val intent = Intent(this, Layout_GoodBye::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_blur, R.anim.slide_blur)
        }

//        Get data from database "User"
        database = FirebaseDatabase.getInstance().getReference("User")
        database.child(info).get().addOnSuccessListener {

            if (it.exists()) {

                val firstname = it.child("firstname").value.toString().trim()
                val lastname = it.child("lastName").value.toString().trim()
                var money = it.child("money").value.toString().trim()
                val iduser = it.child("iduser").value.toString().trim()

                storage = FirebaseStorage.getInstance().getReference("User/$iduser")
                val localFile = File.createTempFile("tempfile", ".jpeg")
                storage.getFile(localFile).addOnSuccessListener {

                    val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    viewuserimage.setImageBitmap(bitmap)
                }.addOnFailureListener {

                    Toast.makeText(this, "Fail to upload an image", Toast.LENGTH_SHORT).show()
                }

                tvusername.setText(firstname + " " + lastname)
                tvusermoney.setText("$ " + money)
            }
        }
    }
}