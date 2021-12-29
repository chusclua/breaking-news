package com.chus.clua.breakingnews.di

import com.chus.clua.breakingnews.data.local.GsonFileReader
import com.chus.clua.breakingnews.data.mapper.ArticleToNewsMapper
import com.chus.clua.breakingnews.data.network.NewsClient
import com.chus.clua.breakingnews.data.repository.CategoriesRepositoryImp
import com.chus.clua.breakingnews.data.repository.CountriesRepositoryImp
import com.chus.clua.breakingnews.data.repository.NewsRepositoryImp
import com.chus.clua.breakingnews.domain.repository.CategoriesRepository
import com.chus.clua.breakingnews.domain.repository.CountriesRepository
import com.chus.clua.breakingnews.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideNewsRepository(
        client: NewsClient,
        mapper: ArticleToNewsMapper
    ): NewsRepository = NewsRepositoryImp(client, mapper)

    @Provides
    @Singleton
    fun provideCountriesRepository(
        gsonFileReader: GsonFileReader
    ): CountriesRepository = CountriesRepositoryImp(gsonFileReader)

    @Provides
    @Singleton
    fun provideCategoriesRepository(
        gsonFileReader: GsonFileReader
    ): CategoriesRepository = CategoriesRepositoryImp(gsonFileReader)
}