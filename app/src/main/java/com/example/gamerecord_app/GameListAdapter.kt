package com.example.gamerecord_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.gamerecord_app.databinding.ItemLayoutBinding
import com.example.rawgapi.model.Game

class GameListAdapter : ListAdapter<Game, GameViewHolder>(DIFF_CALLBACK) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}

class GameViewHolder(private val binding: ItemLayoutBinding) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
    fun bind(game: Game) {
        binding.game = game
        binding.root.setOnClickListener {
            val action = GameListFragmentDirections.actionGameListFragmentToDetailFragment(game.name, game.id)
            binding.root.findNavController().navigate(action)
        }
        binding.executePendingBindings()
    }

}
