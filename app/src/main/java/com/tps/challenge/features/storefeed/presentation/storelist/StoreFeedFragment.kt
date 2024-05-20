package com.tps.challenge.features.storefeed.presentation.storelist

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tps.challenge.Constants
import com.tps.challenge.R
import com.tps.challenge.core.presentation.base.UiState
import com.tps.challenge.core.presentation.utils.GlideImageLoader
import com.tps.challenge.core.presentation.utils.ImageLoader
import com.tps.challenge.features.storefeed.presentation.storedetails.StoreDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.ArrayList
import javax.inject.Inject

/**
 * Displays the list of Stores with its title, description and the cover image to the user.
 */
@AndroidEntryPoint
class StoreFeedFragment : Fragment() {

    private lateinit var storeFeedAdapter: StoreFeedAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var storeListViewModel: StoreListViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var retryButton: AppCompatButton

    @Inject
    lateinit var imageLoader: ImageLoader

//    private val permissionLauncher = registerForActivityResult(
//        ActivityResultContracts.RequestMultiplePermissions()
//    ) { permissions ->
//        storeListViewModel.onLocationPermissionResult(permissions.any { it.value == true })
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_store_feed, container, false)
        setupUI(view = view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupObserver(view = view)
    }

    private fun setupUI(view: View) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_container)
        recyclerView = view.findViewById(R.id.stores_view)
        progressBar = view.findViewById(R.id.store_progress_bar)
        retryButton = view.findViewById(R.id.store_try_again_button)

        storeFeedAdapter = StoreFeedAdapter(imageLoader) { storeItem ->
            val intent = Intent(requireContext(), StoreDetailActivity::class.java)
            intent.putExtra("STORE_ID", storeItem.id)
            startActivity(intent)
        }

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = storeFeedAdapter
        }

        // Enable if Swipe-To-Refresh functionality will be needed
        swipeRefreshLayout.isEnabled = false
        swipeRefreshLayout.setOnRefreshListener {
            storeListViewModel.useDefaultLocation()
        }
    }

    private fun setupViewModel() {
        storeListViewModel = ViewModelProvider(this)[StoreListViewModel::class.java]
    }

    private fun setupObserver(view: View) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                permissionLauncher.launch(
//                    mutableListOf(Manifest.permission.ACCESS_COARSE_LOCATION).toTypedArray()
//                )
                storeListViewModel.storeListUiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            with(view) {
                                findViewById<ProgressBar>(R.id.store_progress_bar).visibility =
                                    View.GONE
                                findViewById<AppCompatButton>(R.id.store_try_again_button).visibility =
                                    View.GONE
                                findViewById<RecyclerView>(R.id.stores_view).visibility =
                                    View.VISIBLE
                            }
                            storeFeedAdapter.updateStores(it.data.storeList)
                            storeFeedAdapter.notifyDataSetChanged()
                        }

                        is UiState.Loading -> {
                            with(view) {
                                findViewById<ProgressBar>(R.id.store_progress_bar).visibility =
                                    View.VISIBLE
                                findViewById<AppCompatButton>(R.id.store_try_again_button).visibility =
                                    View.GONE
                                findViewById<RecyclerView>(R.id.stores_view).visibility = View.GONE
                            }
                        }

                        is UiState.Error -> {
                            with(view) {
                                findViewById<ProgressBar>(R.id.store_progress_bar).visibility =
                                    View.GONE
                                findViewById<AppCompatButton>(R.id.store_try_again_button).visibility =
                                    View.VISIBLE
                                findViewById<RecyclerView>(R.id.stores_view).visibility = View.GONE
                            }
                            Toast.makeText(
                                requireContext(),
                                it.message.asString(requireContext()),
                                Toast.LENGTH_SHORT
                            )
                        }

                        is UiState.ShowSnackbar -> {
                            Toast.makeText(
                                requireContext(),
                                it.message.asString(requireContext()),
                                Toast.LENGTH_SHORT
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "StoreFeedFragment"
    }
}
