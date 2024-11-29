package com.example.rawgapi

import com.example.rawgapi.model.GameResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RAWGApiService {
    @GET("games")
    fun getGames(
        @Query("key") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 40
    ): Call<GameResponse>
}
