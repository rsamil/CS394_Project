package com.example.gamerecord_app

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.gamerecord_app.databinding.FragmentDetailBinding
import com.example.rawgapi.model.Game

class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.gameId = args.gameId
        viewModel.loadGameDetails(args.gameId)
        viewModel.loadUserRating(requireContext(), args.gameId)

        setupObservers()
        setupStarClickListeners()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.gameDetails.observe(viewLifecycleOwner) { game ->
            game?.let { bindGameDetails(it) }
        }

        viewModel.userRating.observe(viewLifecycleOwner) { rating ->
            updateStarImages(rating)
            binding.gameRating.text = "$rating / 5"
            viewModel.saveUserRating(requireContext(), args.gameId, rating)
        }
    }

    private fun bindGameDetails(game: Game) {
        Glide.with(requireContext())
            .load(game.background_image)
            .placeholder(R.drawable.placeholder_image)
            .into(binding.gamePoster)

        binding.gameTitle.text = game.name
        binding.gameReleaseDate.text = "(${game.released?.substring(0, 4) ?: "Unknown"})"
        binding.gameProducer.text = "Producer: ${game.developers?.joinToString(", ") { it.name } ?: "Unknown Developer"}"
        binding.gamePlatforms.text = "Platforms: ${game.platforms?.map { it.platform.name }?.joinToString(", ") ?: "Unknown Platforms"}"
        binding.commentCount.text = game.ratings_count?.toString() ?: "0"
        binding.gameDuration.text = "${game.playtime ?: 0}h"
        binding.gameDescription.text = game.description_raw ?: "No description available."
        binding.gameDescription.movementMethod = ScrollingMovementMethod()
    }

    private fun setupStarClickListeners() {
        binding.star1.setOnClickListener { handleStarClick(1) }
        binding.star2.setOnClickListener { handleStarClick(2) }
        binding.star3.setOnClickListener { handleStarClick(3) }
        binding.star4.setOnClickListener { handleStarClick(4) }
        binding.star5.setOnClickListener { handleStarClick(5) }
    }

    private fun handleStarClick(starNumber: Int) {
        val currentRating = viewModel.userRating.value ?: 0f

        viewModel.setUserRating(
            when {
                currentRating == starNumber.toFloat() -> starNumber - 0.5f //
                currentRating == starNumber - 0.5f -> 0f
                else -> starNumber.toFloat()
            }
        )
    }


    private fun updateStarImages(rating: Float) {
        val fullStar = R.drawable.ic_star_full
        val halfStar = R.drawable.ic_star_half
        val emptyStar = R.drawable.ic_star_empty

        listOf(binding.star1, binding.star2, binding.star3, binding.star4, binding.star5)
            .forEachIndexed { index, star ->
                star.setImageResource(
                    when {
                        rating >= index + 1 -> fullStar
                        rating > index -> halfStar
                        else -> emptyStar
                    }
                )
            }
    }



}
