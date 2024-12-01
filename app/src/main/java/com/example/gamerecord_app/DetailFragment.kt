package com.example.gamerecord_app

import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        apiService = ApiClient.retrofit.create(RAWGApiService::class.java)

        viewModel.gameId = args.gameId

        fetchGameDetails(args.gameId)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.backButton.setOnClickListener {
            callback.handleOnBackPressed()
        }

        val savedRating = loadUserRating(args.gameId)
        if (savedRating >= 0f) {
            viewModel.setUserRating(savedRating)
        }

        viewModel.userRating.observe(viewLifecycleOwner, Observer { rating ->
            updateStarImages(rating)
            binding.gameRating.text = "$rating / 5"
            saveUserRating(args.gameId, rating)
        })

        viewModel.userRating.observe(viewLifecycleOwner, Observer { rating ->
            updateStarImages(rating)
            binding.gameRating.text = "$rating / 5"
        })

        setupStarClickListeners()

        return binding.root
    }

    private fun fetchGameDetails(gameId: Int) {
        apiService = ApiClient.retrofit.create(RAWGApiService::class.java)
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


        val platformNames = game.platforms?.map { it.platform.name }?.joinToString(", ") ?: "Unknown Platforms"
        binding.gamePlatforms.text = "Platforms: $platformNames"

        val ratingsCount = game.ratings_count ?: 0
        binding.commentCount.text = ratingsCount.toString()

        val playtime = game.playtime ?: 0
        binding.gameDuration.text = "${playtime}h"

        binding.gameDescription.text = game.description_raw ?: "No description available."
        binding.gameDescription.movementMethod = ScrollingMovementMethod()

        val currentRating = viewModel.userRating.value ?: 0f
        binding.gameRating.text = "$currentRating / 5"
    }

    private fun setupStarClickListeners() {
        binding.star1.setOnClickListener {
            handleStarClick(1)
        }
        binding.star2.setOnClickListener {
            handleStarClick(2)
        }
        binding.star3.setOnClickListener {
            handleStarClick(3)
        }
        binding.star4.setOnClickListener {
            handleStarClick(4)
        }
        binding.star5.setOnClickListener {
            handleStarClick(5)
        }
    }

    private fun handleStarClick(starNumber: Int) {
        val currentRating = viewModel.userRating.value ?: 0f

        if (currentRating == starNumber.toFloat()) {

            viewModel.setUserRating(starNumber - 0.5f)

        } else if (currentRating == starNumber - 0.5f) {

            viewModel.setUserRating(0f)

        } else {

            viewModel.setUserRating(starNumber.toFloat())

        }
    }

    private fun updateStarImages(rating: Float) {
        val fullStar = R.drawable.ic_star_full
        val halfStar = R.drawable.ic_star_half
        val emptyStar = R.drawable.ic_star_empty

        binding.star1.setImageResource(
            when {
                rating >= 1 -> fullStar
                rating == 0.5f -> halfStar
                else -> emptyStar
            }
        )

        binding.star2.setImageResource(
            when {
                rating >= 2 -> fullStar
                rating == 1.5f -> halfStar
                else -> emptyStar
            }
        )

        binding.star3.setImageResource(
            when {
                rating >= 3 -> fullStar
                rating == 2.5f -> halfStar
                else -> emptyStar
            }
        )

        binding.star4.setImageResource(
            when {
                rating >= 4 -> fullStar
                rating == 3.5f -> halfStar
                else -> emptyStar
            }
        )

        binding.star5.setImageResource(
            when {
                rating >= 5 -> fullStar
                rating == 4.5f -> halfStar
                else -> emptyStar
            }
        )
    }

    private fun saveUserRating(gameId: Int, rating: Float) {
        val sharedPref = requireActivity().getSharedPreferences("user_ratings", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putFloat("game_$gameId", rating)
            apply()
        }
    }

    private fun loadUserRating(gameId: Int): Float {
        val sharedPref = requireActivity().getSharedPreferences("user_ratings", Context.MODE_PRIVATE)
        return sharedPref.getFloat("game_$gameId", -1f)
    }
}
