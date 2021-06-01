package com.onboarding.data.remote

import com.onboarding.model.UserModel
import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {
    @GET("/api/users/2")
    fun getUser(): Call<UserModel>
}