package com.example.gamerecord_app.data

data class GameResponse(
    val results: List<Game>,
    val next: String?
)
