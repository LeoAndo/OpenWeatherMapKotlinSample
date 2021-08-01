package com.example.openweathermapkotlinsample.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openweathermapkotlinsample.R
import com.example.openweathermapkotlinsample.databinding.FragmentHomeBinding
import com.example.openweathermapkotlinsample.domain.WEEKLY_ITEM_COUNT
import com.example.openweathermapkotlinsample.ui.HomeWeatherItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val homeViewModel by viewModels<HomeViewModel>()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val weatherAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view).apply {
            this.viewModel = homeViewModel
            setupWeather(this.listWeather)
        }
        observeLiveData()
    }

    private fun observeLiveData() {
        homeViewModel.weatherModels.observe(viewLifecycleOwner, { weatherModels ->
            weatherAdapter.update(weatherModels.map { weatherModel -> HomeWeatherItem(weatherModel) })
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupWeather(listWeather: RecyclerView) {
        weatherAdapter.apply {
            setOnItemClickListener { item, view ->
                // findNavController().navigate(HomeFragmentDirections.openWeatherDetailFragmentAction())
            }
        }
        listWeather.also {
            it.adapter = weatherAdapter
            val linearLayoutManager = object : GridLayoutManager(
                context, WEEKLY_ITEM_COUNT, LinearLayoutManager.VERTICAL, false
            ) {
                // スクロールなし
                override fun canScrollVertically(): Boolean = false
                override fun canScrollHorizontally(): Boolean = false
            }
            it.layoutManager = linearLayoutManager
        }
    }

    companion object {
        private const val LOG_TAG = "HomeFragment"
    }
}