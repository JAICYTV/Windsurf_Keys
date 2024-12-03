package com.windsurf.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.windsurf.app.data.model.CommunityPost
import com.windsurf.app.data.model.PostType
import com.windsurf.app.data.repository.CommunityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val communityRepository: CommunityRepository
) : ViewModel() {

    private val _posts = MutableStateFlow<List<CommunityPost>>(emptyList())
    val posts: StateFlow<List<CommunityPost>> = _posts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _currentTab = MutableStateFlow(0)
    val currentTab: StateFlow<Int> = _currentTab.asStateFlow()

    init {
        fetchPosts()
    }

    fun setCurrentTab(tabPosition: Int) {
        _currentTab.value = tabPosition
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                when (_currentTab.value) {
                    0 -> communityRepository.getAllPosts().collectLatest { posts ->
                        _posts.value = posts
                    }
                    1 -> communityRepository.getEvents().collectLatest { events ->
                        _posts.value = events
                    }
                    2 -> communityRepository.getGroups().collectLatest { groups ->
                        _posts.value = groups
                    }
                    else -> _posts.value = emptyList()
                }
            } catch (e: Exception) {
                _error.value = "Failed to load posts: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun likePost(postId: String) {
        viewModelScope.launch {
            try {
                communityRepository.likePost(postId)
                fetchPosts()
            } catch (e: Exception) {
                _error.value = "Failed to like post: ${e.localizedMessage}"
            }
        }
    }
}
