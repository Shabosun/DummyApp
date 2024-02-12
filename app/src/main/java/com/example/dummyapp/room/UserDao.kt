package com.example.dummyapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(user : UserDB)
//
//    @Query("SELECT * FROM user_table WHERE userId = :id")
//    suspend fun getUserById(id : Long) : UserDB

}