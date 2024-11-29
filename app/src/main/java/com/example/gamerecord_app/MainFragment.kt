package com.example.gamerecord_app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gamerecord_app.databinding.FragmentMainBinding
import com.example.rawgapi.ApiClient
import com.example.rawgapi.RAWGApiService
import com.example.rawgapi.model.Game
import com.example.rawgapi.model.GameResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {

    private val apiKey = "14db16342d0a4b6bbac323f2b50bb201"
    private var currentPage = 1
    private val pageSize = 40
    private val maxPages = 5
    private val gamesList = mutableListOf<Game>()
    private lateinit var adapter: ItemAdapter
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 4)

        adapter = ItemAdapter(gamesList)
        binding.recyclerView.adapter = adapter

        fetchPage(currentPage)

        return binding.root
    }

    private fun fetchPage(page: Int) {
        val apiService = ApiClient.retrofit.create(RAWGApiService::class.java)

        apiService.getGames(apiKey, page, pageSize).enqueue(object : Callback<GameResponse> {
            override fun onResponse(call: Call<GameResponse>, response: Response<GameResponse>) {
                if (response.isSuccessful) {
                    val newGames = response.body()?.results ?: emptyList()
                    gamesList.addAll(newGames)
                    adapter.notifyItemRangeInserted(gamesList.size - newGames.size, newGames.size)
                    newGames.forEach { game ->
                        Log.d("RAWG_API", "Game: ${game.name}, Released: ${game.released}")
                    }
                    if (page < maxPages) {
                        currentPage++
                        fetchPage(currentPage)
                    }
                } else {
                    Log.e("RAWG_API", "API Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<GameResponse>, t: Throwable) {
                Log.e("RAWG_API", "Network Error: ${t.message}")
            }
        })
    }
}