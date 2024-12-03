package com.windsurf.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

class EventsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_events, container, false)
        
        val actionButton = view.findViewById<MaterialButton>(R.id.actionButton)
        actionButton.setOnClickListener {
            // Play pop animation
            val popAnimation = AnimationUtils.loadAnimation(context, R.anim.button_pop)
            it.startAnimation(popAnimation)
            
            // Show toast message
            Toast.makeText(context, "Loading Events...", Toast.LENGTH_SHORT).show()
        }
        
        return view
    }
}
