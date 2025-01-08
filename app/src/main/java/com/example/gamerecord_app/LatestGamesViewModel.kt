package com.example.gamerecord_app

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rawgapi.ApiClient
import com.example.rawgapi.RAWGApiService
import com.example.rawgapi.model.Game
import com.example.rawgapi.model.GameResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LatestGamesViewModel : ViewModel() {
    private val apiKey = "14db16342d0a4b6bbac323f2b50bb201"
    private val _latestGamesList = MutableLiveData<List<Game>>()
    val latestGamesList: LiveData<List<Game>> get() = _latestGamesList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        fetchLatestGames()
    }

    private fun fetchLatestGames() {
        _isLoading.value = true
        val dates = "2024-01-01,2024-12-31"
        val ordering = "-ratings_count"
        val apiService = ApiClient.retrofit.create(RAWGApiService::class.java)

        apiService.getLatestGames(apiKey, dates, ordering, 1, 40)
            .enqueue(object : Callback<GameResponse> {
                override fun onResponse(call: Call<GameResponse>, response: Response<GameResponse>) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _latestGamesList.value = response.body()?.results ?: emptyList()
                        response.body()?.results?.forEach { game ->
                            Log.d("RAWG_API_LATEST", "Game: ${game.name}, Released: ${game.released}")
                        }
                    } else {
                        Log.e("RAWG_API_LATEST", "API Error: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<GameResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e("RAWG_API_LATEST", "Network Error: ${t.message}")
                }
            })
    }
}
