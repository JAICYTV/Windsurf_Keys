package com.windsurf.app.data.repository

import com.windsurf.app.data.model.CommunityPost
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {
    fun getAllPosts(): Flow<List<CommunityPost>>
    fun getEvents(): Flow<List<CommunityPost>>
    fun getGroups(): Flow<List<CommunityPost>>
    suspend fun createPost(post: CommunityPost)
    suspend fun likePost(postId: String)
    suspend fun commentOnPost(postId: String, comment: String)
}
