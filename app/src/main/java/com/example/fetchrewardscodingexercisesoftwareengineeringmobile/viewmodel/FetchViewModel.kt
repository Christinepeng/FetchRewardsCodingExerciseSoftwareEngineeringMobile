package com.example.fetchrewardscodingexercisesoftwareengineeringmobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchrewardscodingexercisesoftwareengineeringmobile.model.ListItem
import com.example.fetchrewardscodingexercisesoftwareengineeringmobile.repository.FetchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FetchViewModel @Inject constructor(
    private val repository: FetchRepository
) : ViewModel() {
    private val _listItems = MutableStateFlow<List<ListItem>>(emptyList())
    val listItems: StateFlow<List<ListItem>> = _listItems

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        fetchListItems()
    }

    fun fetchListItems() {
        viewModelScope.launch {
            try {
                val items = repository.getListItems()
                _listItems.value = items
                _errorMessage.value = null // Clear any previous errors
            } catch (e: Exception) {
//                Log.e("FetchViewModel", "Error fetching list items", e)
                _errorMessage.value = "Failed to load data. Please try again."
            }
        }
    }
}
