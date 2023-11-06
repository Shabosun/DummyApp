package com.example.dummyapp.retrofit.model

data class Cart(
    val id : Int,
    val products: List<Product>,
    val total : Int,
    val discountedTotal : Int,
    val userId : Int,
    val totalProducts: Int,
    val totalQuantity : Int
)
{

}
