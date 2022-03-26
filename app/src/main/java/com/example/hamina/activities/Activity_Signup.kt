package com.example.hamina.activities

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.Window
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.hamina.R

import com.example.hamina.databinding.ActivitySignupBinding
import com.example.hamina.units.User
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.*


class Activity_Signup : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var storage: StorageReference
    private lateinit var imageUri: Uri
    private lateinit var dialog: Dialog

    companion object {
        private val REQUEST_PERMISSION_REQUEST_CODE = 2020
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

//      Permission firebase with
        database = FirebaseDatabase.getInstance().getReference("User")

////        Spinner
////        ___________Colors
//        val arrayColor = arrayOf("Red", "White", "Black", "Yellow", "Green", "Purple", "Blue", "Orange", "Pink", "Gray")
//        val adapterColor =
//            ArrayAdapter(
//                this@Activity_AddProduct,
//                android.R.layout.simple_spinner_dropdown_item,
//                arrayColor
//            )
//        binding.productColor.adapter = adapterColor
//        binding.productColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(p0: AdapterView<*>?) {}
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {}
//        }

//      Excuting the event
        binding.btnAdd.setOnClickListener {

            val formatname = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss:SSSS", Locale.getDefault())
            val now = Date()
            var filename = formatname.format(now)


            showDialog()
            val phonenumber = binding.phonenumber.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val fname = binding.firstname.text.toString().trim()
            val lname = binding.lastname.text.toString().trim()
            val gmail = binding.gmail.text.toString().trim()
            val address = binding.address.text.toString().trim()
            val money = 0
            val totalused = 0

            val user = User(
                filename,
                phonenumber,
                password,
                fname,
                lname,
                gmail,
                address,
                money,
                totalused
            )

            if (phonenumber.length != 10) {

                hideDialog()
                binding.phonenumber.text.clear()
                Toast.makeText(
                    this,
                    "(Phonenumber): Only 10 characters",
                    Toast.LENGTH_SHORT
                ).show()
            } else
                database.child(phonenumber).get().addOnSuccessListener {

                    if (it.exists()) {
                        hideDialog()
                        binding.phonenumber.text.clear()
                        Toast.makeText(
                            this,
                            "(Phonenumber): Phonenumber has used already!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else
                        if (password.length < 8 || password.length > 20) {

                            hideDialog()
                            binding.password.text?.clear()
                            Toast.makeText(
                                this,
                                "(Password): At least 8 characters and no more than 20 characters",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else
                            if (fname.length < 2) {

                                hideDialog()
                                binding.firstname.text.clear()
                                Toast.makeText(
                                    this,
                                    "(First name): At least 2 characters",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else
                                if (lname.length < 2) {

                                    hideDialog()
                                    binding.lastname.text.clear()
                                    Toast.makeText(
                                        this,
                                        "(Last name): At least 2 characters",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else
                                    if (address.isEmpty()) {

                                        hideDialog()
                                        Toast.makeText(
                                            this,
                                            "(Address): Allow to get current location",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {

                                        database.child(phonenumber).setValue(user)
                                            .addOnCompleteListener {
                                                if (it.isSuccessful) {

                                                    hideDialog()
                                                    binding.firstname.text.clear()
                                                    binding.lastname.text.clear()
                                                    binding.gmail.text.clear()
                                                    binding.phonenumber.text.clear()
                                                    binding.address.setText("")
                                                    binding.password.text?.clear()
                                                    Toast.makeText(
                                                        this@Activity_Signup,
                                                        "Successfully",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                        .show()
                                                    uploadImgUser(filename)
                                                    binding.profileImage.setImageURI(Uri.parse("android.resource://$packageName/${R.drawable.img_addphoto}"))
                                                } else {

                                                    hideDialog()
                                                    Toast.makeText(
                                                        this@Activity_Signup,
                                                        "Failed to create new account",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                    }

                }.addOnFailureListener {

                    hideDialog()
                    Toast.makeText(
                        this@Activity_Signup,
                        "Failed to create new account",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            hideDialog()
        }

        binding.addGalley.setOnClickListener {

            showImagePicker.launch("image/*")
        }

        binding.btnGetlocation.setOnClickListener {

            showDialog()
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                ActivityCompat.requestPermissions(
                    this@Activity_Signup,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION_REQUEST_CODE
                )

            }
            getLocationUser()
        }
    }

    //    Getting current location
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_REQUEST_CODE && grantResults.size > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                getLocationUser()
            else
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getLocationUser() {

        var locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val geocoder = Geocoder(this, Locale.getDefault())
        var addresses: List<Address>

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }

        LocationServices.getFusedLocationProviderClient(this@Activity_Signup)
            .requestLocationUpdates(locationRequest, object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)
                    LocationServices.getFusedLocationProviderClient(this@Activity_Signup)
                        .removeLocationUpdates(this)
                    if (!p0.equals(null) && p0.locations.size >= 0) {
                        var locationIndex = p0.locations.size - 1
                        var latitude = p0.locations.get(locationIndex).latitude
                        var longitude = p0.locations.get(locationIndex).longitude

                        addresses = geocoder.getFromLocation(latitude, longitude, 1)
                        var address: String = addresses[0].getAddressLine(0)
                        binding.address.text = address
                    }
                }
            }, Looper.getMainLooper()!!)

        hideDialog()
        Toast.makeText(this, "Get current location successfully", Toast.LENGTH_SHORT).show()
    }

    //      Picking an image
    private val showImagePicker = registerForActivityResult(ActivityResultContracts.GetContent()) {

        imageUri = it
        binding.profileImage.setImageURI(imageUri)
    }


    private fun uploadImgUser(filename: String) {

        storage = FirebaseStorage.getInstance().reference.child("User/$filename")
        storage.putFile(imageUri).addOnSuccessListener {

            binding.profileImage.setImageURI(Uri.parse("android.resource://$packageName/${R.drawable.img_addphoto}"))
            hideDialog()
        }.addOnFailureListener {

            hideDialog()
        }
    }

    //    Showing a dialog
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





