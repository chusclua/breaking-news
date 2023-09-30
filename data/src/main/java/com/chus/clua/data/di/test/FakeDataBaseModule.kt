package com.chus.clua.data.di.test

import com.chus.clua.data.db.NewsDao
import com.chus.clua.data.db.fake.FakeNewsDao
import com.chus.clua.data.di.DatabaseModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
class FakeDataBaseModule {
    @Provides
    fun provideNewsDao(): NewsDao {
        return FakeNewsDao()
    }
}