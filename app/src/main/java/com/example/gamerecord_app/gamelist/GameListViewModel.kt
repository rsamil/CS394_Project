package com.example.gamerecord_app.gamelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gamerecord_app.api.ApiClient
import com.example.gamerecord_app.api.RAWGApiService
import com.example.gamerecord_app.data.Game
import com.example.gamerecord_app.data.GameResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameListViewModel : ViewModel() {
    private var currentPage = 1
    private val maxPages = 5
    private val pageSize = 40
    private val allGames = mutableListOf<Game>()

    private val _gameList = MutableLiveData<List<Game>>()
    val gameList: LiveData<List<Game>> get() = _gameList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        fetchGames()
    }

    fun fetchGames() {
        if (currentPage > maxPages) return

        _isLoading.value = true
        val apiService = ApiClient.retrofit.create(RAWGApiService::class.java)

        apiService.getGames(currentPage, pageSize).enqueue(object : Callback<GameResponse> {
            override fun onResponse(call: Call<GameResponse>, response: Response<GameResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val newGames = response.body()?.results ?: emptyList()
                    allGames.addAll(newGames)
                    _gameList.value = allGames

                    currentPage++
                    if (currentPage <= maxPages) {
                        fetchGames()
                    }
                }
            }
            override fun onFailure(call: Call<GameResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

}
