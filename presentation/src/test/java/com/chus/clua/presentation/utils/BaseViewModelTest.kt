package com.chus.clua.presentation.utils

import dagger.hilt.android.testing.HiltAndroidRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Rule

open class BaseViewModelTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val testDispatcher = UnconfinedTestDispatcher()

    protected fun setMain() {
        Dispatchers.setMain(testDispatcher)
    }

    protected fun resetMain() {
        Dispatchers.resetMain()
    }
}