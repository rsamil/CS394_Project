package com.example.gamerecord_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailViewModel : ViewModel() {

    private val _userRating = MutableLiveData<Float>()
    val userRating: LiveData<Float> get() = _userRating

    var gameId: Int = 0

    init {
        _userRating.value = 0f
    }

    fun setUserRating(rating: Float) {
        _userRating.value = rating
    }
}
