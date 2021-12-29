package com.chus.clua.breakingnews.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

// Parent class that implements the necessary Rules for LiveData and Coroutines to deal with the JVM
open class BaseViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()
}