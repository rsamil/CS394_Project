package com.example.gamerecord_app

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.gamerecord_app.databinding.FragmentDetailBinding
import com.example.rawgapi.ApiClient
import com.example.rawgapi.RAWGApiService
import com.example.rawgapi.model.Game
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : Fragment() {

    private val apiKey = "14db16342d0a4b6bbac323f2b50bb201"
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailBinding
    private lateinit var apiService: RAWGApiService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        apiService = ApiClient.retrofit.create(RAWGApiService::class.java)

        fetchGameDetails(args.gameId)

        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }

    private fun fetchGameDetails(gameId: Int) {
        apiService.getGameDetails(gameId, apiKey).enqueue(object : Callback<Game> {
            override fun onResponse(call: Call<Game>, response: Response<Game>) {
                if (response.isSuccessful) {
                    val game = response.body()
                    if (game != null) {
                        bindGameDetails(game)
                    }
                } else {
                    Log.e("RAWG_API_DETAIL", "API Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Game>, t: Throwable) {
                Log.e("RAWG_API_DETAIL", "Network Error: ${t.message}")
            }
        })
    }

    private fun bindGameDetails(game: Game) {

        Glide.with(requireContext())
            .load(game.background_image)
            .placeholder(R.drawable.placeholder_image)
            .into(binding.gamePoster)

        binding.gameTitle.text = game.name

        binding.gameReleaseDate.text = "(${game.released?.substring(0, 4) ?: "Unknown"})"

        val developerNames = game.developers?.joinToString(", ") { it.name } ?: "Unknown Developer"
        binding.gameProducer.text = "Producer: $developerNames"

        val rating = game.rating ?: 0.0
        val ratingsCount = game.ratings_count ?: 0
        binding.gameRating.text = "$rating (${ratingsCount} ratings)"

        val platformNames = game.platforms?.map { it.platform.name }?.joinToString(", ") ?: "Unknown Platforms"
        binding.gamePlatforms.text = "Platforms: $platformNames"

        binding.commentCount.text = ratingsCount.toString()

        val playtime = game.playtime ?: 0
        binding.gameDuration.text = "${playtime}h"

        binding.gameDescription.text = game.description_raw ?: "No description available."
        binding.gameDescription.movementMethod = ScrollingMovementMethod()
    }
}
