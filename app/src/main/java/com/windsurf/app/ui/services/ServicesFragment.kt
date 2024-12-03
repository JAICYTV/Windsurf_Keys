package com.windsurf.app.ui.services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.windsurf.app.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ServicesFragment : Fragment() {
    private val viewModel: ServicesViewModel by viewModels()
    private lateinit var adapter: ServicesAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressIndicator: CircularProgressIndicator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_services, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews(view)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupViews(view: View) {
        recyclerView = view.findViewById(R.id.servicesRecyclerView)
        progressIndicator = view.findViewById(R.id.progressIndicator)
    }

    private fun setupRecyclerView() {
        adapter = ServicesAdapter { service ->
            findNavController().navigate(
                R.id.actionServicesFragmentToServiceDetailFragment,
                Bundle().apply {
                    putString("serviceId", service.id)
                }
            )
        }
        
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ServicesFragment.adapter
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.services.collectLatest { services ->
                adapter.submitList(services)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collectLatest { isLoading ->
                progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }
}
