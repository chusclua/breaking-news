package com.chus.clua.breakingnews.di

import android.content.Context
import com.chus.clua.breakingnews.data.local.GsonFileReader
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GsonReaderModule {

    @Provides
    @Singleton
    fun provideGsonFileReader(@ApplicationContext appContext: Context) =
        GsonFileReader(appContext, Gson())
}