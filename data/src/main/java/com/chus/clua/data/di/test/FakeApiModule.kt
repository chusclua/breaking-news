package com.chus.clua.data.di.test

import com.chus.clua.data.network.fake.FakeNewsApiImp
import com.chus.clua.data.di.ApiModule
import com.chus.clua.data.network.NewsApi
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ApiModule::class]
)
abstract class FakeApiModule {
    @Binds
    @Singleton
    abstract fun provideNewsApi(fakeNewsApiImp: FakeNewsApiImp): NewsApi
}