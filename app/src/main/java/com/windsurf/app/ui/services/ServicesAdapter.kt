package com.windsurf.app.ui.services

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.windsurf.app.R
import com.windsurf.app.data.model.Service

class ServicesAdapter(
    private val onServiceClick: (Service) -> Unit
) : ListAdapter<Service, ServicesAdapter.ServiceViewHolder>(ServiceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_service, parent, false)
        return ServiceViewHolder(view, onServiceClick)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ServiceViewHolder(
        view: View,
        private val onServiceClick: (Service) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val cardView: MaterialCardView = view.findViewById(R.id.serviceCard)
        private val nameText: TextView = view.findViewById(R.id.serviceName)
        private val descriptionText: TextView = view.findViewById(R.id.serviceDescription)
        private val priceText: TextView = view.findViewById(R.id.servicePrice)

        fun bind(service: Service) {
            nameText.text = service.name
            descriptionText.text = service.description
            priceText.text = "$${service.price}"

            cardView.setOnClickListener {
                onServiceClick(service)
            }
        }
    }

    private class ServiceDiffCallback : DiffUtil.ItemCallback<Service>() {
        override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean {
            return oldItem == newItem
        }
    }
}
