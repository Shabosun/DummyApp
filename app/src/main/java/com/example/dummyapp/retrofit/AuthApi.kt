package com.example.dummyapp.retrofit

import com.example.dummyapp.retrofit.model.AuthRequest
import com.example.dummyapp.retrofit.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun auth(@Body authRequest: AuthRequest) : Response<User>


}