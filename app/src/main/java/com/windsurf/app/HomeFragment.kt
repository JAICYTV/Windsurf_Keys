package com.windsurf.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.imageview.ShapeableImageView

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize views
        val profileImage = view.findViewById<ShapeableImageView>(R.id.profileImage)
        val userName = view.findViewById<TextView>(R.id.userName)
        val notificationButton = view.findViewById<ImageButton>(R.id.notificationButton)
        val chatButton = view.findViewById<ImageButton>(R.id.chatButton)
        val categoryChipGroup = view.findViewById<ChipGroup>(R.id.categoryChipGroup)

        // Set user name
        userName.text = "Wind Surfer"

        // Set click listeners
        notificationButton.setOnClickListener {
            Toast.makeText(context, "Notifications coming soon!", Toast.LENGTH_SHORT).show()
        }

        chatButton.setOnClickListener {
            Toast.makeText(context, "Chat feature coming soon!", Toast.LENGTH_SHORT).show()
        }

        // Handle category selection
        categoryChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val chip = group.findViewById<Chip>(checkedIds[0])
                when (chip.text.toString()) {
                    "Rentals" -> handleRentalsCategory()
                    "Lessons" -> handleLessonsCategory()
                    "Events" -> handleEventsCategory()
                    "Services" -> handleServicesCategory()
                }
            }
        }

        return view
    }

    private fun handleRentalsCategory() {
        Toast.makeText(context, "Browse available windsurfing equipment", Toast.LENGTH_SHORT).show()
    }

    private fun handleLessonsCategory() {
        Toast.makeText(context, "Book windsurfing lessons", Toast.LENGTH_SHORT).show()
    }

    private fun handleEventsCategory() {
        Toast.makeText(context, "View upcoming windsurfing events", Toast.LENGTH_SHORT).show()
    }

    private fun handleServicesCategory() {
        Toast.makeText(context, "Explore windsurfing services", Toast.LENGTH_SHORT).show()
    }
}
