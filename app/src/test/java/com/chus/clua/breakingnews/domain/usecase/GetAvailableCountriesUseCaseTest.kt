package com.chus.clua.breakingnews.domain.usecase

import com.chus.clua.breakingnews.domain.model.Country
import com.chus.clua.breakingnews.domain.repository.CountriesRepository
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
class GetAvailableCountriesUseCaseTest: BaseTest() {

    @Mock
    private lateinit var repository: CountriesRepository

    private lateinit var useCase: GetAvailableCountriesUseCase

    @Before
    fun setUp() {
        useCase = GetAvailableCountriesUseCase(repository)
    }

    @Test
    fun `WHEN GetAvailableCountriesUseCase is invoked THEN returns a Country list`() =
        testCoroutineDispatcher.runBlockingTest {
            val countryList = listOf(Country.getDefault(), Country("Spain", "ES"), Country("Argentina", "AR"))
            whenever(repository.getAvailableCountries()).thenReturn(countryList)
            val result = useCase()
            assertEquals(countryList.sortedBy { it.name }, result)
        }
}