package com.windsurf.app.di

import com.windsurf.app.data.repository.ServiceRepository
import com.windsurf.app.data.repository.ServiceRepositoryImpl
import com.windsurf.app.data.repository.CommunityRepository
import com.windsurf.app.data.repository.CommunityRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindServiceRepository(
        serviceRepositoryImpl: ServiceRepositoryImpl
    ): ServiceRepository

    @Binds
    @Singleton
    abstract fun bindCommunityRepository(
        communityRepositoryImpl: CommunityRepositoryImpl
    ): CommunityRepository
}
