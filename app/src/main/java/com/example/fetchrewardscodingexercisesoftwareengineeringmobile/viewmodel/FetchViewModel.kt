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

    init {
        fetchListItems()
    }

    private fun fetchListItems() {
        viewModelScope.launch {
            val items = repository.getListItems()
            _listItems.value = items
        }
    }
}


//@HiltViewModel
//class FetchViewModel @Inject constructor(private val repository: FetchRepository) : ViewModel() {
//
//    private val _listItems = MutableStateFlow<List<ListItem>>(emptyList())
//    val listItems: StateFlow<List<ListItem>> = _listItems
//
//    init {
//        fetchListItems()
//    }
//
//    private fun fetchListItems() {
//        viewModelScope.launch {
//            try {
//                val items = repository.getListItems()
//                _listItems.value = items
//            } catch (e: Exception) {
//                // Handle error
//            }
//        }
//    }
//}
