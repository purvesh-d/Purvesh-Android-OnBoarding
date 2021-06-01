package com.onboarding.model


import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("support")
    val support: Support
)