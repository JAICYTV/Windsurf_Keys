package com.windsurf.app.data.repository

import com.windsurf.app.data.model.CommunityPost
import com.windsurf.app.data.model.PostType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommunityRepositoryImpl @Inject constructor() : CommunityRepository {
    // Temporary in-memory storage until we implement proper backend
    private val posts = mutableListOf<CommunityPost>()

    init {
        // Add some sample data
        posts.addAll(listOf(
            CommunityPost(
                id = "1",
                type = PostType.REGULAR,
                title = "New to Windsurfing",
                content = "Hi everyone! Just started windsurfing last week. Any tips for beginners?",
                authorName = "Sarah Chen",
                authorAvatar = null,
                timestamp = Date(),
                likes = 5,
                comments = 3
            ),
            CommunityPost(
                id = "2",
                type = PostType.EVENT,
                title = "Weekend Windsurf Meetup",
                content = "Join us this Saturday for a group session! All skill levels welcome.",
                authorName = "Mike Wilson",
                authorAvatar = null,
                timestamp = Date(),
                likes = 15,
                comments = 7
            ),
            CommunityPost(
                id = "3",
                type = PostType.GROUP,
                title = "Advanced Techniques Group",
                content = "A group for discussing and sharing advanced windsurfing techniques.",
                authorName = "Lisa Rodriguez",
                authorAvatar = null,
                timestamp = Date(),
                likes = 20,
                comments = 0
            )
        ))
    }

    override fun getAllPosts(): Flow<List<CommunityPost>> = flow {
        emit(posts.sortedByDescending { it.timestamp })
    }

    override fun getEvents(): Flow<List<CommunityPost>> = flow {
        emit(posts.filter { it.type == PostType.EVENT }
            .sortedByDescending { it.timestamp })
    }

    override fun getGroups(): Flow<List<CommunityPost>> = flow {
        emit(posts.filter { it.type == PostType.GROUP }
            .sortedByDescending { it.timestamp })
    }

    override suspend fun createPost(post: CommunityPost) {
        posts.add(post)
    }

    override suspend fun likePost(postId: String) {
        val post = posts.find { it.id == postId } ?: return
        val index = posts.indexOf(post)
        posts[index] = post.copy(likes = post.likes + 1)
    }

    override suspend fun commentOnPost(postId: String, comment: String) {
        val post = posts.find { it.id == postId } ?: return
        val index = posts.indexOf(post)
        posts[index] = post.copy(comments = post.comments + 1)
    }
}
