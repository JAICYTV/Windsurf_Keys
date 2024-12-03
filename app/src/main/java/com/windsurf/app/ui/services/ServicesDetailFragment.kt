package com.windsurf.app.ui.services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.windsurf.app.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ServicesDetailFragment : Fragment() {
    private val viewModel: ServicesDetailViewModel by viewModels()
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var serviceImageView: ImageView
    private lateinit var progressIndicator: CircularProgressIndicator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_service_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupViews(view)
        observeViewModel()
        
        arguments?.getString("serviceId")?.let { serviceId ->
            viewModel.loadServiceDetails(serviceId)
        }
    }

    private fun setupViews(view: View) {
        titleTextView = view.findViewById(R.id.serviceTitleTextView)
        descriptionTextView = view.findViewById(R.id.serviceDescriptionTextView)
        priceTextView = view.findViewById(R.id.servicePriceTextView)
        serviceImageView = view.findViewById(R.id.serviceImageView)
        progressIndicator = view.findViewById(R.id.detailProgressIndicator)
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.serviceDetail.collectLatest { service ->
                service?.let {
                    titleTextView.text = it.name 
                    descriptionTextView.text = it.description
                    priceTextView.text = getString(R.string.price_format, it.price)
                    // TODO: Load image using Glide or Coil
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collectLatest { isLoading ->
                progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.error.collectLatest { error ->
                error?.let {
                    Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}
