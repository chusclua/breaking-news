package com.chus.clua.presentation.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.chus.clua.domain.usecase.DeleteOnWatchListUseCase
import com.chus.clua.domain.usecase.GetNewsUseCase
import com.chus.clua.domain.usecase.IsOnWatchListUseCase
import com.chus.clua.domain.usecase.SaveToWatchListUseCase
import com.chus.clua.presentation.utils.BaseViewModelTest
import com.chus.clua.presentation.utils.NewsUITest
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import javax.inject.Inject
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
class NewsDetailViewModelTest : BaseViewModelTest() {

    @Inject
    lateinit var getNewsUseCase: GetNewsUseCase

    @Inject
    lateinit var isOnWatchListUseCase: IsOnWatchListUseCase

    @Inject
    lateinit var saveToWatchListUseCase: SaveToWatchListUseCase

    @Inject
    lateinit var deleteOnWatchListUseCase: DeleteOnWatchListUseCase

    private lateinit var viewModel: NewsDetailViewModel

    @Before
    fun setUp() {
        setMain()
        hiltRule.inject()
        viewModel = NewsDetailViewModel(
            SavedStateHandle(mapOf(Pair("url", NewsUITest.url))),
            getNewsUseCase,
            isOnWatchListUseCase,
            saveToWatchListUseCase,
            deleteOnWatchListUseCase
        )
    }

    @After
    fun tearDown() {
        resetMain()
    }

    @Test
    fun `when viewModel is initialized with an Either Right then it has a News in watchlist`() =
        runTest {
            advanceUntilIdle()
            viewModel.newsFlow.test {
                assertEquals(
                    DetailViewState(
                        news = NewsUITest.copy(isOnWatchList = true),
                        error = false
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when viewModel is initialized with an Either Right then it has a News not in watchlist`() =
        runTest {
            viewModel = NewsDetailViewModel(
                SavedStateHandle(mapOf(Pair("url", "url to test"))),
                getNewsUseCase,
                isOnWatchListUseCase,
                saveToWatchListUseCase,
                deleteOnWatchListUseCase
            )
            advanceUntilIdle()
            viewModel.newsFlow.test {
                assertEquals(DetailViewState(news = NewsUITest, error = false), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when viewModel is initialized with an Either Left then it has an error`() =
        runTest {
            viewModel = NewsDetailViewModel(
                SavedStateHandle(mapOf(Pair("url", "other url"))),
                getNewsUseCase,
                isOnWatchListUseCase,
                saveToWatchListUseCase,
                deleteOnWatchListUseCase
            )
            advanceUntilIdle()
            viewModel.newsFlow.test {
                assertEquals(DetailViewState(news = null, error = true), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when viewModel toggleOnWatchList then it add this to watchlist`() =
        runTest {
            viewModel.toggleOnWatchList(NewsUITest)
            advanceUntilIdle()
            viewModel.newsFlow.test {
                assertEquals(
                    DetailViewState(
                        news = NewsUITest.copy(isOnWatchList = true),
                        error = false
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when viewModel toggleOnWatchList then it remove this from watchlist`() =
        runTest {
            viewModel.toggleOnWatchList(NewsUITest.copy(isOnWatchList = true))
            advanceUntilIdle()
            viewModel.newsFlow.test {
                assertEquals(
                    DetailViewState(
                        news = NewsUITest.copy(isOnWatchList = false),
                        error = false
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
}