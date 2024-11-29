package com.example.rawgapi.model

data class Game(
    val id: Int,
    val name: String,
    val released: String?,
    val background_image: String?,

    val platforms: List<PlatformWrapper>? ,
    val genres: List<Genre>?
)
data class Genre(
    val id: Int,
    val name: String,
    val slug: String
)




data class PlatformWrapper(
    val platform: Platform
)

data class Platform(
    val id: Int,
    val name: String,
    val slug: String


)