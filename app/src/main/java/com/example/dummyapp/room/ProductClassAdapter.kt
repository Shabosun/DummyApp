package com.example.dummyapp.room

import com.example.dummyapp.retrofit.model.Product

interface ProductClassAdapter {

    fun toProductRetrofitModel() : Product

    //fun toProductRoomModel(productDB : ProductDB) : Product
}