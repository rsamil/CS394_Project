package com.example.gamerecord_app

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

class GameListViewModel : ViewModel() {
    private val _gameList = MutableLiveData<List<Game>>()
    val gameList: LiveData<List<Game>> get() = _gameList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val apiService: RAWGApiService = ApiClient.apiService

    init {
        fetchGames()
    }

    fun fetchGames() {
        _isLoading.value = true
        apiService.getGames().enqueue(object : Callback<GameResponse> {
            override fun onResponse(call: Call<GameResponse>, response: Response<GameResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _gameList.value = response.body()?.results ?: emptyList()
                }
            }

            override fun onFailure(call: Call<GameResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

}
