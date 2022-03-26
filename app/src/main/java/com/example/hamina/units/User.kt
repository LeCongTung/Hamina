package com.example.hamina.units

data class User(
    val iduser: String ?= null,
    val phonenumber: String ?= null,
    val password: String ?= null,
    val firstname: String ?= null,
    val lastName: String ?= null,
    val usergmail: String ?= null,
    val addressuser: String ?= null,
    val money: Int ?= null,
    val totalused: Int ?= null
)
