package com.example.hamina.activities

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.*
import com.example.hamina.R
import com.example.hamina.shows.Show_Detail
import com.example.hamina.shows.Show_ListProduct
import com.example.hamina.units.Cart
import com.example.hamina.units.TotalPrice
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Activity_BuyProduct : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var databaseCart: DatabaseReference
    private lateinit var databaseTotal: DatabaseReference
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_product)

//      Init
        val tvproductname: TextView = findViewById(R.id.product_name)
        val tvproductdescription: TextView = findViewById(R.id.product_description)

        val tvmaterial: TextView = findViewById(R.id.product_material)
        val tvmadein: TextView = findViewById(R.id.product_madein)

        val btnS: Button = findViewById(R.id.btn_sizeS)
        val btnM: Button = findViewById(R.id.btn_sizeM)
        val btnL: Button = findViewById(R.id.btn_sizeL)
        val btnXL: Button = findViewById(R.id.btn_sizeXL)

        val tvquantity: TextView = findViewById(R.id.product_quantity)
        val btnMinus: ImageButton = findViewById(R.id.btn_minus)
        val btnPlus: ImageButton = findViewById(R.id.btn_plus)

        val btnBack: ImageButton = findViewById(R.id.btn_back)
        val btnAddToCart: Button = findViewById(R.id.btn_addCart)
        val tvproductprice: TextView = findViewById(R.id.product_price)

        val linkimage: TextView = findViewById(R.id.link_photomain)

        //        Get info user
        val type = intent.getStringExtra("type").toString()
        val pertype = intent.getStringExtra("pertype").toString()
        val product = intent.getStringExtra("product").toString()
        val info = intent.getStringExtra("info").toString()

        val nameproduct = type + pertype
        database = FirebaseDatabase.getInstance().getReference(nameproduct)

        databaseCart = FirebaseDatabase.getInstance().getReference("Cart/$info/List")
        databaseTotal = FirebaseDatabase.getInstance().getReference("Cart/$info")

        database.child(product).get().addOnSuccessListener {

            if (it.exists()) {

                val name = it.child("name").value.toString().trim()
                val description = it.child("description").value.toString().trim()
                val colormain = it.child("colormain").value.toString().trim()
                val colorsecond = it.child("colorsecond").value.toString().trim()
                val material = it.child("material").value.toString().trim()
                val madein = it.child("madein").value.toString().trim()
                val price = it.child("price").value.toString().trim()
                val photomain = it.child("photomain").value.toString().trim()

                tvproductname.setText(name)
                tvproductdescription.setText(description)
                tvmaterial.setText("Material: " + material)
                tvmadein.setText("Made in: " + madein)
                tvproductprice.setText(price)
                tvproductdescription.setText(description)
                linkimage.setText(photomain)

                getColors(colormain, colorsecond)

//              Set quantities and final price product
                var valueQuantity = tvquantity.text.toString().toInt()
                var valueAllProduct = price.toInt()
                val valuePriceNoChange = valueAllProduct

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

            val intent = Intent(this, Show_Detail::class.java)
            intent.putExtra("type", type)
            intent.putExtra("pertype", pertype)
            intent.putExtra("product", product)
            intent.putExtra("info", info)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_back, R.anim.slide_back2)
        }

//        Get size of prodcut
        var valueSize = ""
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


//      Excute event -- Add to cart
        btnAddToCart.setOnClickListener {

            if (valueSize.equals("")) {

                Toast.makeText(
                    this,
                    "You need to choose a size before add to the cart",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val name = tvproductname.text.toString().trim()
                val quantity = tvquantity.text.toString().toInt()
                val total = tvproductprice.text.toString().toInt()
                val linkimage = linkimage.text.toString().trim()

                val cart = Cart(name, valueSize, linkimage, quantity, total)
                val saveName = name + "_" + valueSize

                showDialog()
                databaseCart.child(saveName).get().addOnSuccessListener {

                    if (it.exists()) {

                        Toast.makeText(
                            this,
                            "This product has already existed in cart",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        databaseCart.child(saveName).setValue(cart).addOnCompleteListener {
                            if (it.isSuccessful) {

                                databaseTotal.child("total").get().addOnSuccessListener {

                                    if (it.exists()) {

                                        var availableValue = it.child("total").value.toString().toInt()
                                        availableValue += total
                                        val totalprice = TotalPrice(availableValue)

                                        databaseTotal.child("total").setValue(totalprice).addOnCompleteListener {
                                                if (it.isSuccessful) {

                                                } else {

                                                    Toast.makeText(
                                                        this,
                                                        "Failed to add to cart",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }.addOnFailureListener {
                                                Toast.makeText(this, "Fail to connect!", Toast.LENGTH_SHORT).show()
                                        }
                                    }else{

                                        val totalprice = TotalPrice(total)
                                        databaseTotal.child("total").setValue(totalprice).addOnCompleteListener {
                                            if (it.isSuccessful) {

                                            } else {

                                                Toast.makeText(
                                                    this,
                                                    "Failed to add to cart",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }.addOnFailureListener {
                                            Toast.makeText(this, "Fail to connect!", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            } else {

                                Toast.makeText(this, "Failed to add to cart", Toast.LENGTH_SHORT)
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
                    hideDialog()
                    Toast.makeText(this, "Add to cart successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Show_ListProduct::class.java)
                    intent.putExtra("type", type)
                    intent.putExtra("pertype", pertype)
                    intent.putExtra("info", info)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_back, R.anim.slide_back2)
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

    private fun getColors(colormain: String, colorsecond: String) {

        val vcolormain: ImageView = findViewById(R.id.product_colormain)
        val vcolorsecond: ImageView = findViewById(R.id.product_colorsecond)

        when (colormain) {

            "black" -> vcolormain.setBackgroundColor(getColor(R.color.black))
            "white" -> vcolormain.setBackgroundColor(getColor(R.color.white))
            "pink" -> vcolormain.setBackgroundColor(getColor(R.color.pink))
            "blue" -> vcolormain.setBackgroundColor(getColor(R.color.blue))
            "lightblue" -> vcolormain.setBackgroundColor(getColor(R.color.lightblue))
            "red" -> vcolormain.setBackgroundColor(getColor(R.color.red))
            "orange" -> vcolormain.setBackgroundColor(getColor(R.color.orange))
            "yellow" -> vcolormain.setBackgroundColor(getColor(R.color.yellow))
            "green" -> vcolormain.setBackgroundColor(getColor(R.color.green))
            "purple" -> vcolormain.setBackgroundColor(getColor(R.color.purple))
            "gray" -> vcolormain.setBackgroundColor(getColor(R.color.heading))
            "brown" -> vcolormain.setBackgroundColor(getColor(R.color.brown))
        }

        when (colorsecond) {

            "black" -> vcolorsecond.setBackgroundColor(getColor(R.color.black))
            "white" -> vcolorsecond.setBackgroundColor(getColor(R.color.white))
            "pink" -> vcolorsecond.setBackgroundColor(getColor(R.color.pink))
            "blue" -> vcolorsecond.setBackgroundColor(getColor(R.color.blue))
            "lightblue" -> vcolorsecond.setBackgroundColor(getColor(R.color.lightblue))
            "red" -> vcolorsecond.setBackgroundColor(getColor(R.color.red))
            "orange" -> vcolorsecond.setBackgroundColor(getColor(R.color.orange))
            "yellow" -> vcolorsecond.setBackgroundColor(getColor(R.color.yellow))
            "green" -> vcolorsecond.setBackgroundColor(getColor(R.color.green))
            "purple" -> vcolorsecond.setBackgroundColor(getColor(R.color.purple))
            "gray" -> vcolorsecond.setBackgroundColor(getColor(R.color.heading))
            "brown" -> vcolorsecond.setBackgroundColor(getColor(R.color.brown))
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