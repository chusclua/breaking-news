package com.chus.clua.presentation.news

import androidx.paging.testing.asSnapshot
import app.cash.turbine.test
import com.chus.clua.domain.usecase.DeleteAllWatchListUseCase
import com.chus.clua.domain.usecase.DeleteOnWatchListUseCase
import com.chus.clua.domain.usecase.GetTopHeadLinesNewsUseCase
import com.chus.clua.domain.usecase.GetWatchListUseCase
import com.chus.clua.domain.usecase.SaveToWatchListUseCase
import com.chus.clua.presentation.models.Category
import com.chus.clua.presentation.models.NewsUI
import com.chus.clua.presentation.utils.BaseViewModelTest
import com.chus.clua.presentation.utils.NewsUITest
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import javax.inject.Inject
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
class NewsViewModelTest : BaseViewModelTest() {

    @Inject
    lateinit var getTopHeadLinesNewsUseCase: GetTopHeadLinesNewsUseCase

    @Inject
    lateinit var getWatchListUseCase: GetWatchListUseCase

    @Inject
    lateinit var saveToWatchListUseCase: SaveToWatchListUseCase

    @Inject
    lateinit var deleteOnWatchListUseCase: DeleteOnWatchListUseCase

    @Inject
    lateinit var deleteAllWatchListUseCase: DeleteAllWatchListUseCase

    private lateinit var viewModel: NewsViewModel

    @Before
    fun setUp() {
        setMain()
        hiltRule.inject()
        viewModel = NewsViewModel(
            getTopHeadLinesNewsUseCase,
            getWatchListUseCase,
            saveToWatchListUseCase,
            deleteOnWatchListUseCase,
            deleteAllWatchListUseCase
        )
    }

    @After
    fun tearDown() {
        resetMain()
    }

    @Test
    fun `when viewModel is initialized then it has a correct number of items`() = runTest {
        advanceUntilIdle()
        val items: List<NewsUI> = viewModel.newsFlow.asSnapshot {
            scrollTo(20)
        }
        assertEquals(60, items.size)
        assertEquals(NewsUITest.copy(isOnWatchList = true), items.firstOrNull())
    }

    @Test
    fun `when viewModel is initialized then it has a watchlist`() = runTest {
        advanceUntilIdle()
        viewModel.viewStateFlow.test {
            assertEquals(NewsUITest.copy(isOnWatchList = true), awaitItem().watchList.first())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when viewModel toggleOnWatchList then it remove this from watchlist`() = runTest {
        advanceUntilIdle()

        viewModel.toggleOnWatchList(NewsUITest.copy(isOnWatchList = true))

        advanceUntilIdle()

        viewModel.viewStateFlow.test {
            assertTrue(awaitItem().watchList.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when viewModel toggleOnWatchList then it add this to watchlist`() = runTest {
        advanceUntilIdle()

        viewModel.toggleOnWatchList(NewsUITest)

        advanceUntilIdle()

        viewModel.viewStateFlow.test {
            val watchList = awaitItem().watchList
            assertTrue(watchList.size == 2)
            assertTrue(watchList.contains(NewsUITest.copy(isOnWatchList = true)))
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `when viewModel clearAll then it remove all watchlist`() = runTest {
        advanceUntilIdle()

        viewModel.clearAllWatchList()

        advanceUntilIdle()

        viewModel.viewStateFlow.test {
            assertTrue(awaitItem().watchList.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `when viewModel is initialized then it has correct bottomBar index`() = runTest {
        advanceUntilIdle()

        viewModel.viewStateFlow.test {
            assertEquals(0, awaitItem().bottomBarIndex)
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.onIndexSelected(16)

        viewModel.viewStateFlow.test {
            assertEquals(16, awaitItem().bottomBarIndex)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when viewModel onCategoryClick then it has a correct category list index`() = runTest {
        advanceUntilIdle()

        viewModel.viewStateFlow.test {
            assertEquals(Category.BUSINESS, awaitItem().selectedCategory)
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.onCategoryClick(Category.ENTERTAINMENT)

        viewModel.viewStateFlow.test {
            assertEquals(Category.ENTERTAINMENT, awaitItem().selectedCategory)
            cancelAndIgnoreRemainingEvents()
        }
    }

}