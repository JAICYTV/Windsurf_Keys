package com.windsurf.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class JobsAdapter(private val jobs: List<Job>) : 
    RecyclerView.Adapter<JobsAdapter.JobViewHolder>() {

    class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.jobTitle)
        val typeText: TextView = itemView.findViewById(R.id.jobType)
        val descriptionText: TextView = itemView.findViewById(R.id.jobDescription)
        val cardView: MaterialCardView = itemView.findViewById(R.id.jobCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_job, parent, false)
        return JobViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = jobs[position]
        holder.titleText.text = job.title
        holder.typeText.text = job.type
        holder.descriptionText.text = job.description
        
        holder.cardView.setOnClickListener {
            // Add click handling if needed
        }
    }

    override fun getItemCount() = jobs.size
}
