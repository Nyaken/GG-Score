package me.nyaken.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.nyaken.domain.repository.SummonerRepository
import me.nyaken.network.ApiContainer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideSearchRepository(
        api: ApiContainer
    ): SummonerRepository {
        return SummonerRepository(api)
    }

}