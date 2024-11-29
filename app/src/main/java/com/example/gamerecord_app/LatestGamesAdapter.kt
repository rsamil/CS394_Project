package com.example.gamerecord_app


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rawgapi.model.Game

class LatestGamesAdapter(private val games: List<Game>) :
    RecyclerView.Adapter<LatestGamesAdapter.LatestGameViewHolder>() {

    class LatestGameViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val gameImageView: ImageView = view.findViewById(R.id.gameImageView)
        val gameNameTextView: TextView = view.findViewById(R.id.gameNameTextView)
        val genresTextView: TextView = view.findViewById(R.id.genresTextView)
        val platformsTextView: TextView = view.findViewById(R.id.platformsTextView)

        fun bind(game: Game) {

            Glide.with(view.context)
                .load(game.background_image)
                .placeholder(R.drawable.placeholder_image)
                .into(gameImageView)


            gameNameTextView.text = game.name


            val genreNames = game.genres?.joinToString(", ") { it.name } ?: "Unknown Genres"
            genresTextView.text = "Genres: $genreNames"


            val platformNames = game.platforms?.map { it.platform.name }?.joinToString(", ") ?: "Unknown Platforms"
            platformsTextView.text = "Platforms: $platformNames"


            view.setOnClickListener {
                val action = LatestGamesFragmentDirections.actionLatestGamesFragmentToDetailFragment(
                    game.name, game.id
                )
                view.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestGameViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_latest_game, parent, false)
        return LatestGameViewHolder(adapterLayout)
    }

    override fun getItemCount() = games.size

    override fun onBindViewHolder(holder: LatestGameViewHolder, position: Int) {
        holder.bind(games[position])
    }
}
