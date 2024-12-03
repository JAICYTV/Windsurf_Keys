package com.windsurf.app.ui.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.windsurf.app.R
import com.windsurf.app.data.model.CommunityPost
import com.windsurf.app.data.model.PostType
import java.text.SimpleDateFormat
import java.util.Locale

class CommunityAdapter(
    private val onLikeClick: (CommunityPost) -> Unit,
    private val onCommentClick: (CommunityPost) -> Unit
) : ListAdapter<CommunityPost, CommunityAdapter.ViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_community_post, parent, false)
        return ViewHolder(view, onLikeClick, onCommentClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        view: View,
        private val onLikeClick: (CommunityPost) -> Unit,
        private val onCommentClick: (CommunityPost) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val titleText: TextView = view.findViewById(R.id.titleText)
        private val contentText: TextView = view.findViewById(R.id.contentText)
        private val authorText: TextView = view.findViewById(R.id.authorText)
        private val timeText: TextView = view.findViewById(R.id.timeText)
        private val typeChip: TextView = view.findViewById(R.id.typeChip)
        private val likeButton: MaterialButton = view.findViewById(R.id.likeButton)
        private val commentButton: MaterialButton = view.findViewById(R.id.commentButton)
        private val postImage: ImageView = view.findViewById(R.id.postImage)
        
        private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
        private var currentPost: CommunityPost? = null

        init {
            likeButton.setOnClickListener {
                currentPost?.let(onLikeClick)
            }
            commentButton.setOnClickListener {
                currentPost?.let(onCommentClick)
            }
        }

        fun bind(post: CommunityPost) {
            currentPost = post
            
            titleText.text = post.title
            contentText.text = post.content
            authorText.text = post.authorName
            timeText.text = dateFormat.format(post.timestamp)
            
            typeChip.apply {
                text = when (post.type) {
                    PostType.EVENT -> "Event"
                    PostType.GROUP -> "Group"
                    PostType.REGULAR -> "Post"
                }
                visibility = if (post.type != PostType.REGULAR) View.VISIBLE else View.GONE
            }
            
            likeButton.text = "${post.likes} Likes"
            commentButton.text = "${post.comments} Comments"
            
            postImage.visibility = if (post.imageUrl != null) View.VISIBLE else View.GONE
            // TODO: Load image using Glide or Coil when imageUrl is not null
        }
    }

    private class PostDiffCallback : DiffUtil.ItemCallback<CommunityPost>() {
        override fun areItemsTheSame(oldItem: CommunityPost, newItem: CommunityPost): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CommunityPost, newItem: CommunityPost): Boolean {
            return oldItem == newItem
        }
    }
}
