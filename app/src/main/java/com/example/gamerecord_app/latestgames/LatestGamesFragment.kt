package com.example.gamerecord_app.latestgames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamerecord_app.databinding.FragmentLatestGamesBinding

class LatestGamesFragment : Fragment() {
    private lateinit var binding: FragmentLatestGamesBinding
    private val viewModel: LatestGamesViewModel by viewModels()
    private lateinit var adapter: LatestGamesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLatestGamesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        adapter = LatestGamesAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.latestGames.observe(viewLifecycleOwner) { games ->
            adapter.submitList(games)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        return binding.root
    }
}
