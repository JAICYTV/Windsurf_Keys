package com.windsurf.app.ui.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.windsurf.app.data.model.Service
import com.windsurf.app.data.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServicesDetailViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository
) : ViewModel() {

    private val _serviceDetail = MutableStateFlow<Service?>(null)
    val serviceDetail: StateFlow<Service?> = _serviceDetail.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadServiceDetails(serviceId: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                
                serviceRepository.getServiceById(serviceId)
                    .catch { e -> 
                        _error.value = e.message ?: "Failed to load service details"
                    }
                    .collect { service ->
                        _serviceDetail.value = service
                    }
            } finally {
                _isLoading.value = false
            }
        }
    }
}
