package com.example.gamerecord_app.latestgames

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gamerecord_app.api.ApiClient
import com.example.gamerecord_app.data.Game
import com.example.gamerecord_app.data.GameResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LatestGamesViewModel : ViewModel() {
    private val _latestGames = MutableLiveData<List<Game>>()
    val latestGames: LiveData<List<Game>> get() = _latestGames


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        fetchLatestGames()
    }

    private fun fetchLatestGames() {
        _isLoading.value = true
        val dates = "2025-01-01,2025-12-31"
        val ordering = "-ratings_count"

        ApiClient.apiService.getLatestGames(dates, ordering, 1, 40)
            .enqueue(object : Callback<GameResponse> {
                override fun onResponse(call: Call<GameResponse>, response: Response<GameResponse>) {
                    if (response.isSuccessful) {
                        _latestGames.value = response.body()?.results ?: emptyList()
                    }
                    _isLoading.value = false
                }

                override fun onFailure(call: Call<GameResponse>, t: Throwable) {
                    _isLoading.value = false
                }
            })

    }
}
