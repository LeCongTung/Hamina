package com.example.hamina.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import com.example.hamina.R
import com.example.hamina.layouts.Layout_Information
import com.example.hamina.layouts.Layout_Store
import com.example.hamina.units.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Activity_AddMoney : AppCompatActivity() {

    private lateinit var databaseUser: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_money)

//        Init
        val btnBack: ImageButton = findViewById(R.id.btn_back)

        val valueMoney: EditText = findViewById(R.id.addMoney)

        val idfirst: TextView = findViewById(R.id.firstid)
        val idsecond: TextView = findViewById(R.id.secondtid)
        val idthird: TextView = findViewById(R.id.thirdtid)
        val idfourth: TextView = findViewById(R.id.fourthtid)

        val displaymoney: TextView = findViewById(R.id.user_money)
        val displayadd: TextView = findViewById(R.id.display_addmoney)
        val displayfinal: TextView = findViewById(R.id.display_finalmoney)

        val btnAdd: Button = findViewById(R.id.btn_addMoney)

//      Get info user
        val info = intent.getStringExtra("info").toString()

//        Excute event -- Back previous layout
        btnBack.setOnClickListener {

            val intent = Intent(this, Layout_Information::class.java)
            intent.putExtra("info", info)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_back, R.anim.slide_back2)
        }

//        Load data and excute event when click
        databaseUser = FirebaseDatabase.getInstance().getReference("User")
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

                idfirst.setText(phonenumber.substring(0, 3))
                idsecond.setText(phonenumber.substring(3, 6))
                idthird.setText(phonenumber.substring(6, 8))
                idfourth.setText(phonenumber.substring(8))
                displaymoney.setText(money.toString())
                displayfinal.setText(money.toString())

                valueMoney.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {}
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                        if (valueMoney.equals("")){

                            displayadd.setText("0")
                            displayfinal.setText(money.toString())
                        }else{
                            displayadd.setText(p0.toString())
                            displayfinal.setText((money + p0.toString().toInt()).toString())
                        }
                    }
                })

//                Excute event -- Add money to cih
                btnAdd.setOnClickListener {

                    if (valueMoney.text.toString().equals("") ){

                        Toast.makeText(this, "You need to add at least 1$", Toast.LENGTH_SHORT).show()
                    }else{
                        val valueAdd = displayfinal.text.toString().toInt()
                        val user = User(id, phonenumber, password, firstname, lastName, usergmail, addressuser, valueAdd, totalused)
                        databaseUser.child(phonenumber).setValue(user).addOnCompleteListener{

                            if (it.isSuccessful) {
                            } else { Toast.makeText(this, "Error to add money", Toast.LENGTH_SHORT).show() }
                        }

                        Toast.makeText(this, "Add more " + displayadd.text.toString() + "$ into your wallet successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, Layout_Information::class.java)
                        intent.putExtra("info", info)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_back, R.anim.slide_back2)
                    }
                }
            }
        }

    }
}