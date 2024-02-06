package com.example.dummyapp.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first


private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("data_store")
class DataStoreManager(private val context: Context) {

    suspend fun save(key : String, value : String){

        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit {
            settings ->
            settings[dataStoreKey] = value
        }

    }

    suspend fun read(key : String) : String?
    {
            val dataStoreKey = stringPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            return preferences[dataStoreKey]
    }


//    suspend fun saveToken(token : String)
//    {
//        context.dataStore.edit { pref ->
//            pref[stringPreferencesKey("token")] = token
//        }
//    }
//
//    fun getToken() = context.dataStore.data.map {pref  ->
//         return@map pref[stringPreferencesKey("token")]
//    }

}