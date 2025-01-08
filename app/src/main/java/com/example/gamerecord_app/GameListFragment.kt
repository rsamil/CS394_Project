package com.example.gamerecord_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gamerecord_app.databinding.FragmentGameListBinding

class GameListFragment : Fragment() {
    private lateinit var binding: FragmentGameListBinding
    private lateinit var adapter: GameListAdapter
    private lateinit var viewModel: GameListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(GameListViewModel::class.java)

        setupRecyclerView()
        observeViewModel()

        binding.latestGamesButton.setOnClickListener {

            findNavController().navigate(GameListFragmentDirections.actionGameListFragmentToLatestGamesFragment())
        }
        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = GameListAdapter()
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
        binding.recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.gameList.observe(viewLifecycleOwner) { games ->
            adapter.submitList(games)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}
