package com.windsurf.app.ui.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.windsurf.app.data.model.CommunityPost
import com.windsurf.app.data.repository.CommunityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val communityRepository: CommunityRepository
) : ViewModel() {

    private val _posts = MutableStateFlow<List<CommunityPost>>(emptyList())
    val posts: StateFlow<List<CommunityPost>> = _posts

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _currentTab = MutableStateFlow(0)
    val currentTab: StateFlow<Int> = _currentTab

    init {
        loadPosts()
    }

    fun setCurrentTab(position: Int) {
        _currentTab.value = position
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                val flow = when (_currentTab.value) {
                    0 -> communityRepository.getAllPosts()
                    1 -> communityRepository.getEvents()
                    2 -> communityRepository.getGroups()
                    else -> communityRepository.getAllPosts()
                }
                
                flow.catch { e ->
                    _error.value = e.message ?: "An error occurred"
                }.collect { posts ->
                    _posts.value = posts
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
}
