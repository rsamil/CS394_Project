package com.example.rawgapi.model

data class Game(
    val id: Int,
    val name: String,
    val released: String?,
    val background_image: String?,
    val rating: Float?,
    val ratings_count: Int?,
    val platforms: List<PlatformWrapper>?,
    val genres: List<Genre>?,
    val description_raw: String?,
    val developers: List<Developer>?,
    val publishers: List<Publisher>?,
    val playtime: Int?
)

data class PlatformWrapper(
    val platform: Platform
)

data class Platform(
    val id: Int,
    val name: String,
    val slug: String
)

data class Genre(
    val id: Int,
    val name: String,
    val slug: String
)

data class Developer(
    val id: Int,
    val name: String,
    val slug: String
)

data class Publisher(
    val id: Int,
    val name: String,
    val slug: String
)
