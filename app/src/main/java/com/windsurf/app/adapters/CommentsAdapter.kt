package com.windsurf.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class CommentsAdapter(private val comments: List<Comment>) :
    RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val authorText: TextView = itemView.findViewById(R.id.authorText)
        val commentText: TextView = itemView.findViewById(R.id.commentText)
        val ratingText: TextView = itemView.findViewById(R.id.ratingText)
        val cardView: MaterialCardView = itemView.findViewById(R.id.commentCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.authorText.text = comment.author
        holder.commentText.text = comment.text
        holder.ratingText.text = "★".repeat(comment.rating)
    }

    override fun getItemCount() = comments.size
}
