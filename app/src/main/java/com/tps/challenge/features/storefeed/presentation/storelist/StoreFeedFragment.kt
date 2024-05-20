package com.tps.challenge.features.storefeed.presentation.storelist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tps.challenge.R
import com.tps.challenge.core.presentation.base.UiState
import com.tps.challenge.features.storefeed.presentation.storedetails.StoreDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Displays the list of Stores with its title, description and the cover image to the user.
 */
@AndroidEntryPoint
class StoreFeedFragment : Fragment() {
    companion object {
        const val TAG = "StoreFeedFragment"
    }
    private lateinit var storeFeedAdapter: StoreFeedAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var swipeRefreshLayout : SwipeRefreshLayout
    private lateinit var storeListViewModel: StoreListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_store_feed, container, false)
        setupViewModel()
        setupUI(view = view)
        setupObserver(view = view)
        return view
    }

    private fun setupObserver(view: View) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                storeListViewModel.storeListUiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            with(view) {
                                findViewById<ProgressBar>(R.id.store_progress_bar).visibility = View.GONE
                                findViewById<AppCompatButton>(R.id.store_try_again_button).visibility = View.GONE
                                findViewById<RecyclerView>(R.id.stores_view).visibility= View.VISIBLE
                            }
                            storeFeedAdapter.addStores(it.data.storeList)
                            storeFeedAdapter.notifyDataSetChanged()
                        }

                        is UiState.Loading -> {
                            with(view) {
                                findViewById<ProgressBar>(R.id.store_progress_bar).visibility = View.VISIBLE
                                findViewById<AppCompatButton>(R.id.store_try_again_button).visibility = View.GONE
                                findViewById<RecyclerView>(R.id.stores_view).visibility= View.GONE
                            }
                        }

                        is UiState.Error -> {
                            with(view) {
                                findViewById<ProgressBar>(R.id.store_progress_bar).visibility = View.GONE
                                findViewById<AppCompatButton>(R.id.store_try_again_button).visibility = View.VISIBLE
                                findViewById<RecyclerView>(R.id.stores_view).visibility= View.GONE
                            }
                            Toast.makeText(requireContext(), it.message.asString(requireContext()), Toast.LENGTH_SHORT)
                        }

                        is UiState.ShowSnackbar -> {
                            Toast.makeText(requireContext(), it.message.asString(requireContext()), Toast.LENGTH_SHORT)
                        }
                    }
                }
            }
        }
    }

    private fun setupUI(view: View) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_container)
        // Enable if Swipe-To-Refresh functionality will be needed
        swipeRefreshLayout.isEnabled = false

        swipeRefreshLayout.setOnRefreshListener {
            storeListViewModel.fetchStores()
        }

        storeFeedAdapter = StoreFeedAdapter(ArrayList()) {
            val intent = Intent(requireContext(), StoreDetailActivity::class.java)
            intent.putExtra("STORE_ID", it.id)
            intent.putExtra("STORE_NAME", it.name)
            intent.putExtra("STORE_DESCRIPTION", it.description)
            startActivity(intent)
        }
        recyclerView = view.findViewById(R.id.stores_view)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = storeFeedAdapter
        }
    }

    private fun setupViewModel() {
        storeListViewModel = ViewModelProvider(this)[StoreListViewModel::class.java]
    }
}
