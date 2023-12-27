package com.example.dummyapp.room


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserDB::class], version = 1, exportSchema = false)
abstract class DatabaseInstance :  RoomDatabase() {

    abstract val userDao : UserDao


    companion object{
        @Volatile
        private var INSTANCE : DatabaseInstance? = null
        fun getInstance(context : Context) : DatabaseInstance{
            synchronized(this)
            {
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
}