package com.example.dummyapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dummyapp.retrofit.model.Product


@Entity(tableName = "products_table")
data class ProductDB (
    @PrimaryKey
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
    //val images : List<String>
): ProductClassAdapter{

    override fun toProductRetrofitModel() : Product
    {
        return Product(
            this.id,
            this.title,
            this.description,
            this.price,
            this.discountPercentage,
            this.rating,
            this.stock,
            this.brand,
            this.category,
            this.thumbnail,
            emptyList()

        )

    }
}
