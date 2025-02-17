package com.example.gamerecord_app
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gamerecord_app.data.Game
import android.widget.TextView
import com.example.gamerecord_app.data.Genre
import com.example.gamerecord_app.gamelist.GameListAdapter
import com.example.gamerecord_app.latestgames.LatestGamesAdapter

@BindingAdapter("app:gameList")
fun bindGameList(recyclerView: RecyclerView, gameList: List<Game>?) {
    val adapter = recyclerView.adapter as? GameListAdapter
    adapter?.submitList(gameList)
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(url)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.placeholder_image)
            .into(view)
    } else {
        view.setImageResource(R.drawable.placeholder_image)
    }
}

@BindingAdapter("genreListText")
fun bindGenreListText(textView: TextView, genres: List<Genre>?) {
    genres?.let {
        textView.text = it.joinToString(", ") { genre -> genre.name }
    } ?: run {
        textView.text = "Unknown Genres"
    }
}
@BindingAdapter("app:latestGamesList")
fun bindLatestGamesList(recyclerView: RecyclerView, latestGamesList: List<Game>?) {
    val adapter = recyclerView.adapter as? LatestGamesAdapter
    adapter?.submitList(latestGamesList)
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Game>?) {
    val adapter = recyclerView.adapter as? LatestGamesAdapter
    adapter?.submitList(data)
}

