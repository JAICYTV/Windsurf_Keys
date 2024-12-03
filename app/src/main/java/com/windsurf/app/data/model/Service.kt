package com.windsurf.app.data.model

data class Service(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String? = null,
    val category: ServiceCategory
)

enum class ServiceCategory {
    RENTAL,
    LESSONS,
    MAINTENANCE,
    STORAGE
}
