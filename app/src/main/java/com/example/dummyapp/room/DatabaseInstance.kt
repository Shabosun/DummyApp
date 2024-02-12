package com.example.dummyapp.room


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductDB::class], version = 3, exportSchema = false)
abstract class DatabaseInstance :  RoomDatabase() {

    //abstract val userDao : UserDao
    abstract val productDao: ProductDao


    companion object{
        //@Volatile
        private var INSTANCE : DatabaseInstance? = null
        fun getInstance(context : Context) : DatabaseInstance{

                var instance = INSTANCE
                if(instance == null)
                {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseInstance::class.java,
                        "dummy_database")
                        .build()

                    INSTANCE = instance

                }
                return instance
        }
    }
}