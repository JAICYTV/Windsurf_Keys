package com.windsurf.app.ui.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.windsurf.app.data.model.Service
import com.windsurf.app.data.model.ServiceCategory
import com.windsurf.app.data.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServicesViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository
) : ViewModel() {

    private val _services = MutableStateFlow<List<Service>>(emptyList())
    val services: StateFlow<List<Service>> = _services

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadServices()
    }

    fun loadServices(category: ServiceCategory? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                val flow = if (category != null) {
                    serviceRepository.getServicesByCategory(category)
                } else {
                    serviceRepository.getServices()
                }
                
                flow.catch { e ->
                    _error.value = e.message ?: "An error occurred"
                }.collect { services ->
                    _services.value = services
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
}
