package com.example.fetchrewardscodingexercisesoftwareengineeringmobile.repository

import com.example.fetchrewardscodingexercisesoftwareengineeringmobile.model.ListItem
import com.example.fetchrewardscodingexercisesoftwareengineeringmobile.network.FetchService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchRepository @Inject constructor(private val fetchService: FetchService) {

    suspend fun getListItems(): List<ListItem> {
        return fetchService.getListItems()
    }
}
