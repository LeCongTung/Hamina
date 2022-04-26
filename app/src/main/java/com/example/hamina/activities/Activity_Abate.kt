package com.example.hamina.activities

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.*
import com.example.hamina.R
import com.example.hamina.shows.Show_Detail
import com.example.hamina.shows.Show_ListCart
import com.example.hamina.shows.Show_ListProduct
import com.example.hamina.units.Cart
import com.example.hamina.units.TotalPrice
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class Activity_Abate : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var databaseTotal: DatabaseReference
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abate)

//        Init
        val imageMain: ImageView = findViewById(R.id.imageMain)
        val tvproductname: TextView = findViewById(R.id.product_name)

        val btnS: Button = findViewById(R.id.btn_sizeS)
        val btnM: Button = findViewById(R.id.btn_sizeM)
        val btnL: Button = findViewById(R.id.btn_sizeL)
        val btnXL: Button = findViewById(R.id.btn_sizeXL)

        val tvquantity: TextView = findViewById(R.id.product_quantity)
        val tvproductprice: TextView = findViewById(R.id.product_price)
        val btnMinus: ImageButton = findViewById(R.id.btn_minus)
        val btnPlus: ImageButton = findViewById(R.id.btn_plus)

        val btnBack: ImageButton = findViewById(R.id.btn_back)
        val btnDelete: ImageButton = findViewById(R.id.btn_delete)
        val btnEdit: ImageButton = findViewById(R.id.btn_edit)

        val linkimage: TextView = findViewById(R.id.link_photomain)
        val linkmoney: TextView = findViewById(R.id.valuePrice)


        //        Get info user
        val product = intent.getStringExtra("product").toString()
        val size = intent.getStringExtra("size").toString()
        val info = intent.getStringExtra("info").toString()

        database = FirebaseDatabase.getInstance().getReference("Cart/$info/List")
        databaseTotal = FirebaseDatabase.getInstance().getReference("Cart/$info")

        val nameproduct = product + "_" + size
        database.child(nameproduct).get().addOnSuccessListener {

            if (it.exists()) {

                val name = it.child("name").value.toString().trim()
                val photomain = it.child("photomain").value.toString().trim()
                val quantity = it.child("quantity").value.toString().trim()
                val valueSize = it.child("size").value.toString().trim()
                val total = it.child("total").value.toString().trim()

                tvproductname.setText(name)
                tvquantity.setText(quantity)
                tvproductprice.setText(total)
                tvquantity.setText(quantity)
                Picasso.with(imageMain.context).load(photomain).into(imageMain)

                linkimage.setText(photomain)
                linkmoney.setText(total)

                if (valueSize.equals("S")) {

                    btnS.setBackgroundColor(getColor(R.color.heading))
                    btnM.setBackgroundColor(getColor(R.color.black))
                    btnL.setBackgroundColor(getColor(R.color.black))
                    btnXL.setBackgroundColor(getColor(R.color.black))
                } else
                    if (valueSize.equals("M")) {

                        btnS.setBackgroundColor(getColor(R.color.black))
                        btnM.setBackgroundColor(getColor(R.color.heading))
                        btnL.setBackgroundColor(getColor(R.color.black))
                        btnXL.setBackgroundColor(getColor(R.color.black))
                    } else
                        if (valueSize.equals("L")) {

                            btnS.setBackgroundColor(getColor(R.color.black))
                            btnM.setBackgroundColor(getColor(R.color.black))
                            btnL.setBackgroundColor(getColor(R.color.heading))
                            btnXL.setBackgroundColor(getColor(R.color.black))
                        } else {
                            btnS.setBackgroundColor(getColor(R.color.black))
                            btnM.setBackgroundColor(getColor(R.color.black))
                            btnL.setBackgroundColor(getColor(R.color.black))
                            btnXL.setBackgroundColor(getColor(R.color.heading))
                        }

//              Set quantities and final price product
                var valueQuantity = tvquantity.text.toString().toInt()
                var valueAllProduct = total.toInt()
                val valuePriceNoChange = valueAllProduct / valueQuantity

//                Excute event -- Plus or minus a quantity of product
                btnMinus.setOnClickListener {

                    if (valueQuantity > 1) {
                        valueQuantity -= 1
                        valueAllProduct -= valuePriceNoChange
                    } else {
                        valueAllProduct = valuePriceNoChange
                        Toast.makeText(this, "At least 1 product", Toast.LENGTH_SHORT).show()
                    }

                    tvquantity.setText(valueQuantity.toString())
                    tvproductprice.setText(valueAllProduct.toString())
                }

                btnPlus.setOnClickListener {

                    valueQuantity += 1
                    valueAllProduct += valuePriceNoChange

                    tvquantity.setText(valueQuantity.toString())
                    tvproductprice.setText(valueAllProduct.toString())
                }

            } else
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(
                this,
                "Fail to connect!",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnBack.setOnClickListener {

            val intent = Intent(this, Show_ListCart::class.java)
            intent.putExtra("info", info)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_back, R.anim.slide_back2)
        }

//        Choice size of product
        var valueSize = size
        btnS.setOnClickListener {

            btnS.setBackgroundColor(getColor(R.color.heading))
            btnM.setBackgroundColor(getColor(R.color.black))
            btnL.setBackgroundColor(getColor(R.color.black))
            btnXL.setBackgroundColor(getColor(R.color.black))
            valueSize = "S"
        }
        btnM.setOnClickListener {

            btnS.setBackgroundColor(getColor(R.color.black))
            btnM.setBackgroundColor(getColor(R.color.heading))
            btnL.setBackgroundColor(getColor(R.color.black))
            btnXL.setBackgroundColor(getColor(R.color.black))
            valueSize = "M"
        }
        btnL.setOnClickListener {

            btnS.setBackgroundColor(getColor(R.color.black))
            btnM.setBackgroundColor(getColor(R.color.black))
            btnL.setBackgroundColor(getColor(R.color.heading))
            btnXL.setBackgroundColor(getColor(R.color.black))
            valueSize = "L"
        }
        btnXL.setOnClickListener {

            btnS.setBackgroundColor(getColor(R.color.black))
            btnM.setBackgroundColor(getColor(R.color.black))
            btnL.setBackgroundColor(getColor(R.color.black))
            btnXL.setBackgroundColor(getColor(R.color.heading))
            valueSize = "XL"
        }

//        Excute change quantity of product in cart



        btnEdit.setOnClickListener {

            val name = tvproductname.text.toString().trim()
            val quantity = tvquantity.text.toString().toInt()
            val total = tvproductprice.text.toString().toInt()
            val linkimage = linkimage.text.toString().trim()

            val cart = Cart(name, valueSize, linkimage, quantity, total)
            val saveName = name + "_" + valueSize

            showDialog()
            database.child(saveName).get().addOnSuccessListener {

                btnEdit.setImageResource(R.drawable.ic_edit_click_black)
                database.child(nameproduct).removeValue().addOnSuccessListener {}
                database.child(saveName).setValue(cart).addOnCompleteListener {
                    if (it.isSuccessful) {

                        databaseTotal.child("total").get().addOnSuccessListener {

                            if (it.exists()) {

                                val lostPrice = linkmoney.text.toString().toInt()
                                var availableValue = it.child("total").value.toString().toInt()
                                availableValue = availableValue - lostPrice + total
                                val totalprice = TotalPrice(availableValue)

                                databaseTotal.child("total").setValue(totalprice)
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {

                                        } else {

                                            Toast.makeText(
                                                this,
                                                "Failed to add to cart",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }.addOnFailureListener {

                                        Toast.makeText(this, "Fail to connect!", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                            }
                            Toast.makeText(this, "Edit succesfully", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, Show_ListCart::class.java)
                            intent.putExtra("info", info)
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_back, R.anim.slide_back2)
                        }
                    } else {

                        Toast.makeText(this, "Failed to edit", Toast.LENGTH_SHORT)
                            .show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Fail to connect!", Toast.LENGTH_SHORT).show()
                }

                hideDialog()

            }.addOnFailureListener {
                Toast.makeText(
                    this,
                    "Fail to connect!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btnDelete.setOnClickListener {

            btnDelete.setImageResource(R.drawable.ic_delete_click_black)
            database.child(nameproduct).removeValue().addOnSuccessListener {}
            showDialog()
            databaseTotal.child("total").get().addOnSuccessListener {

                if (it.exists()) {

                    val lostPrice = linkmoney.text.toString().toInt()
                    var availableValue = it.child("total").value.toString().toInt()
                    availableValue = availableValue - lostPrice
                    val totalprice = TotalPrice(availableValue)

                    databaseTotal.child("total").setValue(totalprice)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {

                            } else {

                                Toast.makeText(
                                    this,
                                    "Failed to add to cart",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }.addOnFailureListener {

                            Toast.makeText(this, "Fail to connect!", Toast.LENGTH_SHORT)
                                .show()
                        }
                }
                hideDialog()
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Show_ListCart::class.java)
                intent.putExtra("info", info)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_back, R.anim.slide_back2)
            }
        }
    }

    private fun showDialog() {

        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_uploading)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideDialog() {

        dialog.dismiss()
    }
}