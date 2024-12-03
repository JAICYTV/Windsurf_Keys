package com.windsurf.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AvailabilityAdapter(private val availabilityList: List<Availability>) :
    RecyclerView.Adapter<AvailabilityAdapter.AvailabilityViewHolder>() {

    class AvailabilityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayText: TextView = itemView.findViewById(R.id.dayText)
        val hoursText: TextView = itemView.findViewById(R.id.hoursText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvailabilityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_availability, parent, false)
        return AvailabilityViewHolder(view)
    }

    override fun onBindViewHolder(holder: AvailabilityViewHolder, position: Int) {
        val availability = availabilityList[position]
        holder.dayText.text = availability.day
        holder.hoursText.text = availability.hours
    }

    override fun getItemCount() = availabilityList.size
}
