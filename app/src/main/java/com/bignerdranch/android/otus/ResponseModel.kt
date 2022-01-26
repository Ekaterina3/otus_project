package com.bignerdranch.android.otus

import com.google.gson.annotations.SerializedName

data class ResponseModel(
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val image: String,
    @SerializedName("id") val id: Int
)