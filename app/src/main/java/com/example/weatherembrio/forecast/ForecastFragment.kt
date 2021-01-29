package com.example.weatherembrio.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.weatherembrio.databinding.FragmentForecastBinding

class ForecastFragment : Fragment() {

    private lateinit var viewModelFactory: ForecastViewModelFactory

    private val viewModel: ForecastViewModel by viewModels() { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.let {
            viewModelFactory = ForecastViewModelFactory(it.application)
        }

        val binding = FragmentForecastBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = ForecastItemAdapter()
        binding.recyclerView.adapter = adapter

        viewModel.forecastItemList2.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.background.observe(viewLifecycleOwner) {
            it?.let {
                binding.root.setBackgroundColor(it)
            }
        }

        return binding.root
    }

}