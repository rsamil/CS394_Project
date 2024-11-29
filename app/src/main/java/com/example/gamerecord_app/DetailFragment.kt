package com.example.gamerecord_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.gamerecord_app.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        binding.gameNameTextView.text = args.gameName
        binding.gameIdTextView.text = "Game ID: ${args.gameId}"

        return binding.root
    }
}