package com.example.fetchrewardscodingexercisesoftwareengineeringmobile.repository

import com.example.fetchrewardscodingexercisesoftwareengineeringmobile.model.ListItem
import com.example.fetchrewardscodingexercisesoftwareengineeringmobile.network.FetchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response


@ExperimentalCoroutinesApi
class FetchRepositoryTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var fetchService: FetchService
    private lateinit var repository: FetchRepository

    @Before
    fun setUp() {
        fetchService = mock(FetchService::class.java)
        repository = FetchRepository(fetchService)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `getListItems returns data when service call is successful`() = runTest {
        // Given
        val items = listOf(
            ListItem(id = 1, listId = 1, name = "Item 1"),
            ListItem(id = 2, listId = 1, name = "Item 2")
        )
        whenever(fetchService.getListItems()).thenReturn(items)

        // When
        val result = repository.getListItems()
        advanceUntilIdle()

        // Then
        assertEquals(items, result)
    }

    @Test
    fun `getListItems throws exception when service call fails`() = runTest {
        // Given
        val exception = HttpException(Response.error<List<ListItem>>(404, okhttp3.ResponseBody.create(null, "")))
        whenever(fetchService.getListItems()).thenThrow(exception)

        // When & Then
        try {
            repository.getListItems()
            fail("Expected exception to be thrown")
        } catch (e: Exception) {
            assertTrue(e is HttpException)
            assertEquals(404, (e as HttpException).code())
        }
    }
}
