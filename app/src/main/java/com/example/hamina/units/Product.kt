package com.example.hamina.units

data class Product(
    val name: String ?= null,
    val colormain: String ?= null,
    val colorsecond: String ?= null,
    val description: String ?= null,
    val madein: String ?= null,
    val material: String ?= null,
    val photomain: String ?= null,
    val photobehind: String ?= null,
    val photodetail: String ?= null,
    val photomodel: String ?= null,
    val price: Int ?= null,
    val view: Int? = null
)
