package com.example.gamerecord_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rawgapi.model.Game

class ItemAdapter(private val games: List<Game>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val gameImageView: ImageView = view.findViewById(R.id.gameImageView)

        fun bind(game: Game) {
            Glide.with(view.context)
                .load(game.background_image)
                .placeholder(R.drawable.placeholder_image)
                .into(gameImageView)

            view.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToDetailFragment(game.name, game.id)
                view.findNavController().navigate(action)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount() = games.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(games[position])
    }
}
