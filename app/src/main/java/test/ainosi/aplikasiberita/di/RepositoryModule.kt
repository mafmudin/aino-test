package test.ainosi.aplikasiberita.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import test.ainosi.aplikasiberita.data.local.NewsDao
import test.ainosi.aplikasiberita.data.remote.ApiService
import test.ainosi.aplikasiberita.repository.NewsRepository
import test.ainosi.aplikasiberita.repository.NewsRepositoryImpl
import test.ainosi.aplikasiberita.utility.ContextProviders
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideContextProvider(): ContextProviders = ContextProviders.getInstance()

    @Singleton
    @Provides
    fun provideNewsRepositoryImpl(contextProviders: ContextProviders, newsDao: NewsDao, apiService: ApiService) =
        NewsRepositoryImpl(contextProviders, newsDao, apiService) as NewsRepository
}