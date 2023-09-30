package com.chus.clua.data.di

import com.chus.clua.data.repository.NewsRepositoryImp
import com.chus.clua.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideNewsRepository(repositoryImp: NewsRepositoryImp): NewsRepository

}