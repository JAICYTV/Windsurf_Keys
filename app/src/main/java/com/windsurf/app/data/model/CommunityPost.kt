package com.windsurf.app.data.model

import java.util.Date

data class CommunityPost(
    val id: String,
    val type: PostType,
    val title: String,
    val content: String,
    val authorName: String,
    val authorAvatar: String?,
    val timestamp: Date,
    val likes: Int = 0,
    val comments: Int = 0,
    val imageUrl: String? = null
)

enum class PostType {
    REGULAR,
    EVENT,
    GROUP
}
