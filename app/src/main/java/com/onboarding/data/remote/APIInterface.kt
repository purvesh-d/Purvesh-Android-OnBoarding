package com.onboarding.data.remote

import com.onboarding.model.LoginRequestModel
import com.onboarding.model.LoginResponseModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIInterface {
    @POST("/api/login")
    fun loginAsync(@Body requestBody: LoginRequestModel): Deferred<Response<LoginResponseModel>>
}