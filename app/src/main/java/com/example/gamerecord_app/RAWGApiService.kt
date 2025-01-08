// RAWGApiService.kt
package com.example.rawgapi

import com.example.rawgapi.model.Game
import com.example.rawgapi.model.GameResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RAWGApiService {
    @GET("games")
    fun getGames(
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 40
    ): Call<GameResponse>

    @GET("games")
    fun getLatestGames(
        @Query("dates") dates: String?,
        @Query("ordering") ordering: String?,
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 20
    ): Call<GameResponse>

    @GET("games/{id}")
    fun getGameDetails(
        @Path("id") gameId: Int
    ): Call<Game>
}
