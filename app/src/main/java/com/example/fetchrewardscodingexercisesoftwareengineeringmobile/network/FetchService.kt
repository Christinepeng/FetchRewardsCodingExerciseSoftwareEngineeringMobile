package com.example.fetchrewardscodingexercisesoftwareengineeringmobile.network

import com.example.fetchrewardscodingexercisesoftwareengineeringmobile.model.ListItem
import retrofit2.http.GET

interface FetchService {
    @GET("hiring.json")
    suspend fun getListItems(): List<ListItem>
}
