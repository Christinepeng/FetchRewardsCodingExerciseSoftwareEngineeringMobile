package com.example.fetchrewardscodingexercisesoftwareengineeringmobile.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fetchrewardscodingexercisesoftwareengineeringmobile.model.ListItem
import com.example.fetchrewardscodingexercisesoftwareengineeringmobile.repository.FetchRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class FetchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var repository: FetchRepository

    private lateinit var viewModel: FetchViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        // Do not initialize viewModel here
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `fetchListItems emits items when repository returns data`() = runTest {
        // Given
        val items = listOf(
            ListItem(id = 1, listId = 1, name = "Item 1"),
            ListItem(id = 2, listId = 1, name = "Item 2")
        )
        `when`(repository.getListItems()).thenReturn(items)

        // Initialize ViewModel after setting up mock
        viewModel = FetchViewModel(repository)

        // When
        advanceUntilIdle() // Ensure all coroutines have completed

        // Then
        Assert.assertEquals(items, viewModel.listItems.value)
        Assert.assertNull(viewModel.errorMessage.value)
    }

    @Test
    fun `fetchListItems emits error when repository throws exception`() = runTest {
        // Given
        val exception = RuntimeException("Network error")
        `when`(repository.getListItems()).thenThrow(exception)

        // Initialize ViewModel after setting up mock
        viewModel = FetchViewModel(repository)

        // When
        advanceUntilIdle() // Ensure all coroutines have completed

        // Then
        Assert.assertTrue(viewModel.listItems.value.isEmpty())
        Assert.assertEquals("Failed to load data. Please try again.", viewModel.errorMessage.value)
    }
}