package com.example.dummyapp.retrofit.model

import com.example.dummyapp.room.ProductClassAdapter
import com.example.dummyapp.room.ProductDB

data class Product (
    val id : Int,
    val title : String,
    val description : String,
    val price : Int,
    val discountPercentage : Float,
    val rating : Float,
    val stock : Int,
    val brand : String,
    val category : String,
    val thumbnail : String,
    val images : List<String>

){


}