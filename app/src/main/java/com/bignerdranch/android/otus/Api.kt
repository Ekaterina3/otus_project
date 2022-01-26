package com.bignerdranch.android.otus

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {
    @GET("films")
    fun getFilms(): Call<List<ResponseModel>>

    @POST("films")
    fun setFilmById(
        @Query ("id") id: Int,
        @Body film: ResponseModel
    ): Call<ResponseModel>
}