package com.example.openweathermapkotlinsample.ui.home

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openweathermapkotlinsample.R
import com.example.openweathermapkotlinsample.databinding.FragmentHomeBinding
import com.example.openweathermapkotlinsample.domain.WEEKLY_ITEM_COUNT
import com.example.openweathermapkotlinsample.domain.exception.UnAuthorizedException
import com.example.openweathermapkotlinsample.location.AppLocationService
import com.example.openweathermapkotlinsample.ui.HomeWeatherItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val homeViewModel by viewModels<HomeViewModel>()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val weatherAdapter = GroupAdapter<GroupieViewHolder>()

    @Inject
    lateinit var appLocationService: AppLocationService
    private val permissionChecker =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            Log.d(LOG_TAG, "permissionChecker granted: $granted")
            appLocationService.getCurrentLocation()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view).apply {
            this.viewModel = homeViewModel
            setupWeather(this.listWeather)
        }
        observeLiveData()
        permissionChecker.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun observeLiveData() {
        homeViewModel.weatherModels.observe(viewLifecycleOwner, { weatherModels ->
            weatherAdapter.update(weatherModels.map { weatherModel -> HomeWeatherItem(weatherModel) })
        })
        appLocationService.locationResult.observe(viewLifecycleOwner, {
            viewLifecycleOwner.lifecycleScope.launch(coroutineExceptionHandler) {
                doAction { homeViewModel.fetchWeatherInfo() }
            }
        })
        appLocationService.localityName.observe(viewLifecycleOwner, { localityName ->
            binding.textWard.text = localityName
            binding.textWeatherCity.text = localityName
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupWeather(listWeather: RecyclerView) {
        weatherAdapter.apply {
            setOnItemClickListener { item, view ->
                // findNavController().navigate(HomeFragmentDirections.openXXXAction())
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

    private fun <T> doAction(action: suspend () -> T) {
        viewLifecycleOwner.lifecycleScope.launch(coroutineExceptionHandler) {
            // TODO プログレスバーの表示処理を行う  - START
            // binding.progressIndicatorLayout.show()
            action.invoke()
            // binding.progressIndicatorLayout.hide()
            // TODO プログレスバーの表示処理を行う  - END
        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is UnAuthorizedException -> {
                Toast.makeText(
                    requireContext(),
                    "local.propertiesにAPIKEYを設定してください！",
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {
                Toast.makeText(
                    requireContext(),
                    "error: ${throwable.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        Log.e(LOG_TAG, throwable.localizedMessage ?: "no error message..")
    }
}