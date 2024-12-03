package com.windsurf.app

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var currentTooltip: TextView? = null
    private var lastSelectedItemId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setupWithNavController(navController)

        bottomNav.setOnItemSelectedListener { menuItem ->
            if (lastSelectedItemId != menuItem.itemId) {
                // Remove previous tooltip
                currentTooltip?.let { removeTooltip(it) }

                // Animate the selected item
                val menuView = bottomNav.findViewById<View>(menuItem.itemId)
                menuView?.let { animateNavItem(it) }

                // Show tooltip
                showTooltip(menuItem.title.toString(), bottomNav)

                // Handle navigation
                lastSelectedItemId = menuItem.itemId
                navController.navigate(menuItem.itemId)
            }
            true
        }

        // Set initial selection
        bottomNav.selectedItemId = R.id.navigation_home
    }

    private fun animateNavItem(view: View) {
        val scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 1.2f, 1f)
        val scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 1.2f, 1f)
        val alpha = ObjectAnimator.ofFloat(view, View.ALPHA, 1f, 0.8f, 1f)

        AnimatorSet().apply {
            playTogether(scaleX, scaleY, alpha)
            duration = 300
            interpolator = OvershootInterpolator()
            start()
        }
    }

    private fun showTooltip(text: String, anchorView: BottomNavigationView) {
        val tooltip = TextView(this).apply {
            setText(text)
            setTextColor(ContextCompat.getColor(context, android.R.color.white))
            setBackgroundResource(R.drawable.tooltip_background)
            gravity = Gravity.CENTER
            textSize = 14f
            alpha = 0f
            elevation = 8f
        }

        val rootLayout = findViewById<ViewGroup>(android.R.id.content)
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            bottomMargin = anchorView.height + 24
        }

        rootLayout.addView(tooltip, params)

        // Animate tooltip appearance
        val animSet = AnimatorSet()
        val fadeIn = ObjectAnimator.ofFloat(tooltip, View.ALPHA, 0f, 1f)
        val slideUp = ObjectAnimator.ofFloat(tooltip, View.TRANSLATION_Y, 16f, 0f)
        
        animSet.apply {
            playTogether(fadeIn, slideUp)
            duration = 200
            start()
        }

        // Schedule tooltip removal
        tooltip.postDelayed({
            removeTooltip(tooltip)
        }, 1500)

        currentTooltip = tooltip
    }

    private fun removeTooltip(tooltip: TextView) {
        val animSet = AnimatorSet()
        val fadeOut = ObjectAnimator.ofFloat(tooltip, View.ALPHA, 1f, 0f)
        val slideDown = ObjectAnimator.ofFloat(tooltip, View.TRANSLATION_Y, 0f, 16f)
        
        animSet.apply {
            playTogether(fadeOut, slideDown)
            duration = 200
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    (tooltip.parent as? ViewGroup)?.removeView(tooltip)
                    if (currentTooltip == tooltip) {
                        currentTooltip = null
                    }
                }
            })
            start()
        }
    }
}
