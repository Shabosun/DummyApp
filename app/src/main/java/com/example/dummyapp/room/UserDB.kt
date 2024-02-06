package com.example.dummyapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserDB(

    @PrimaryKey(autoGenerate = true)
    val userId : Long = 0L,

    @ColumnInfo(name = "username")
    var username : String,

    @ColumnInfo(name = "password")
    var password : String,

    @ColumnInfo(name = "token")
    var token : String


)


