package com.windsurf.app.data.repository

import com.windsurf.app.data.model.Service
import com.windsurf.app.data.model.ServiceCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceRepositoryImpl @Inject constructor() : ServiceRepository {
    // Temporary mock data until we implement backend integration
    private val mockServices = listOf(
        Service(
            id = "1",
            name = "Beginner Windsurf Lesson",
            description = "Perfect for beginners! Learn the basics of windsurfing with our experienced instructors.",
            price = 75.0,
            category = ServiceCategory.LESSONS
        ),
        Service(
            id = "2",
            name = "Advanced Technique Course",
            description = "Master advanced windsurfing techniques and maneuvers.",
            price = 95.0,
            category = ServiceCategory.LESSONS
        ),
        Service(
            id = "3",
            name = "Board Rental - Full Day",
            description = "High-quality windsurf board rental for a full day of adventure.",
            price = 45.0,
            category = ServiceCategory.RENTAL
        )
    )

    override suspend fun getServices(): Flow<List<Service>> = flow {
        emit(mockServices)
    }

    override suspend fun getServicesByCategory(category: ServiceCategory): Flow<List<Service>> = flow {
        emit(mockServices.filter { it.category == category })
    }

    override suspend fun getServiceById(id: String): Flow<Service?> = flow {
        emit(mockServices.find { it.id == id })
    }
}
