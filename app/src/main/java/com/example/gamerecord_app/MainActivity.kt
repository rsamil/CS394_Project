package com.example.gamerecord_app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.rawgapi.ApiClient
import com.example.rawgapi.RAWGApiService
import com.example.rawgapi.model.GameResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val apiKey = "14db16342d0a4b6bbac323f2b50bb201"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService = ApiClient.retrofit.create(RAWGApiService::class.java)


        apiService.getGames(apiKey).enqueue(object : Callback<GameResponse> {
            override fun onResponse(call: Call<GameResponse>, response: Response<GameResponse>) {
                if (response.isSuccessful) {
                    response.body()?.results?.forEach { game ->
                        Log.d("RAWG_API", "Game: ${game.name}, Released: ${game.released}")
                    }
                } else {
                    Log.e("RAWG_API", "API Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<GameResponse>, t: Throwable) {
                Log.e("RAWG_API", "Network Error: ${t.message}")
            }
        })
    }
}
