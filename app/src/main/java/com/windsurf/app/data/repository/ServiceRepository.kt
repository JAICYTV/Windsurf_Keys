package com.windsurf.app.data.repository

import com.windsurf.app.data.model.Service
import com.windsurf.app.data.model.ServiceCategory
import kotlinx.coroutines.flow.Flow

interface ServiceRepository {
    suspend fun getServices(): Flow<List<Service>>
    suspend fun getServicesByCategory(category: ServiceCategory): Flow<List<Service>>
    suspend fun getServiceById(id: String): Flow<Service?>
}
