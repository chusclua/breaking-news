package com.chus.clua.data.di

import com.chus.clua.domain.model.News
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class CacheModule {

    @Provides
    fun provideMap(): MutableMap<String, News> = mutableMapOf()

}