package com.example.gamerecord_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.gamerecord_app.databinding.ItemLayoutLatestGameBinding
import com.example.rawgapi.model.Game

class LatestGamesAdapter : ListAdapter<Game, LatestGamesViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Game>() {
            override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestGamesViewHolder {
        val binding = ItemLayoutLatestGameBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LatestGamesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LatestGamesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class LatestGamesViewHolder(private val binding: ItemLayoutLatestGameBinding) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

    fun bind(game: Game) {
        binding.game = game

        Glide.with(binding.root.context)
            .load(game.background_image)
            .placeholder(R.drawable.placeholder_image)
            .into(binding.gameImageView)

        binding.gameNameTextView.text = game.name

        val genreNames = game.genres?.joinToString(", ") { it.name } ?: "Unknown Genres"
        binding.genresTextView.text = "Genres: $genreNames"


        val platformNames = game.platforms?.map { it.platform.name }?.joinToString(", ") ?: "Unknown Platforms"
        binding.platformsTextView.text = "Platforms: $platformNames"


        binding.root.setOnClickListener {
            val action = LatestGamesFragmentDirections
                .actionLatestGamesFragmentToDetailFragment(game.name, game.id)
            binding.root.findNavController().navigate(action)
        }

        binding.executePendingBindings()
    }
}
