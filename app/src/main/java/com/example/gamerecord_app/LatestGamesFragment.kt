package com.example.gamerecord_app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamerecord_app.databinding.FragmentLatestGamesBinding
import com.example.rawgapi.ApiClient
import com.example.rawgapi.RAWGApiService
import com.example.rawgapi.model.Game
import com.example.rawgapi.model.GameResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LatestGamesFragment : Fragment() {

    private val apiKey = "14db16342d0a4b6bbac323f2b50bb201"
    private val latestGamesList = mutableListOf<Game>()
    private lateinit var adapter: LatestGamesAdapter
    private lateinit var binding: FragmentLatestGamesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLatestGamesBinding.inflate(inflater, container, false)


        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())


        adapter = LatestGamesAdapter(latestGamesList)
        binding.recyclerView.adapter = adapter


        fetchLatestGames()

        return binding.root
    }

    private fun fetchLatestGames() {
        val dates = "2024-01-01,2024-12-31"
        val ordering = "-ratings_count"
        val apiService = ApiClient.retrofit.create(RAWGApiService::class.java)


        apiService.getLatestGames(apiKey, dates, ordering, 1,40).enqueue(object : Callback<GameResponse> {
            override fun onResponse(call: Call<GameResponse>, response: Response<GameResponse>) {
                if (response.isSuccessful) {
                    val newGames = response.body()?.results ?: emptyList()
                    latestGamesList.addAll(newGames)
                    adapter.notifyDataSetChanged()
                    newGames.forEach { game ->
                        Log.d("RAWG_API_LATEST", "Game: ${game.name}, Released: ${game.released}")
                    }
                } else {
                    Log.e("RAWG_API_LATEST", "API Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<GameResponse>, t: Throwable) {
                Log.e("RAWG_API_LATEST", "Network Error: ${t.message}")
            }
        })
    }
}
