package com.example.gamerecord_app

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rawgapi.ApiClient
import com.example.rawgapi.RAWGApiService
import com.example.rawgapi.model.Game
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _gameDetails = MutableLiveData<Game?>()
    val gameDetails: LiveData<Game?> get() = _gameDetails

    private val _userRating = MutableLiveData<Float>()
    val userRating: LiveData<Float> get() = _userRating

    private val apiService: RAWGApiService = ApiClient.apiService

    var gameId: Int = 0

    init {
        _userRating.value = 0f
    }

    fun loadGameDetails(gameId: Int) {
        apiService.getGameDetails(gameId).enqueue(object : Callback<Game> {
            override fun onResponse(call: Call<Game>, response: Response<Game>) {
                if (response.isSuccessful) {
                    _gameDetails.value = response.body()
                } else {
                    _gameDetails.value = null
                }
            }
            override fun onFailure(call: Call<Game>, t: Throwable) {
                _gameDetails.value = null
            }
        })
    }

    fun setUserRating(rating: Float) {
        _userRating.value = rating
    }

    fun saveUserRating(context: Context, gameId: Int, rating: Float) {
        val sharedPref = context.getSharedPreferences("user_ratings", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putFloat("game_$gameId", rating)
            apply()
        }
    }

    fun loadUserRating(context: Context, gameId: Int) {
        val sharedPref = context.getSharedPreferences("user_ratings", Context.MODE_PRIVATE)
        _userRating.value = sharedPref.getFloat("game_$gameId", 0f)
    }
}
