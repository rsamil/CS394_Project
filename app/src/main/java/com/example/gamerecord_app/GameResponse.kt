package com.example.rawgapi.model

data class GameResponse(
    val results: List<Game>,
    val next: String?
)
