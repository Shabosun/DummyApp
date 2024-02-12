package com.example.dummyapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable


@Dao
interface ProductDao {

    //@Insert
    //suspend fun insert(product : ProductDB)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product : ProductDB) : Completable

    @Query("SELECT * FROM products_table")
    fun getAllProducts() : Flowable<List<ProductDB>>
}