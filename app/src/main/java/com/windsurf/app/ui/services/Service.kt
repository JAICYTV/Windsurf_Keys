package com.windsurf.app.ui.services

data class Service(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val imageUrl: String? = null
)
