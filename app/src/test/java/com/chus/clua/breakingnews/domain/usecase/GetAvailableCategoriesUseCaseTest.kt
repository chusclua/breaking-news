package com.chus.clua.breakingnews.domain.usecase

import com.chus.clua.breakingnews.domain.model.Category
import com.chus.clua.breakingnews.domain.repository.CategoriesRepository
import com.chus.clua.breakingnews.utils.BaseTest
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetAvailableCategoriesUseCaseTest: BaseTest() {

    @Mock
    private lateinit var repository: CategoriesRepository

    private lateinit var useCase: GetAvailableCategoriesUseCase

    @Before
    fun setUp() {
        useCase = GetAvailableCategoriesUseCase(repository)
    }

    @Test
    fun `WHEN GetAvailableCategoriesUseCase is invoked THEN returns a Category list`() =
        testCoroutineDispatcher.runBlockingTest {
            val categoryList = listOf(Category("Business", "business"), Category("Sports", "sports"), Category("General", "general"))
            whenever(repository.getAvailableCategories()).thenReturn(categoryList)
            val result = useCase()
            assertEquals(categoryList.sortedBy { it.name }, result)
        }
}