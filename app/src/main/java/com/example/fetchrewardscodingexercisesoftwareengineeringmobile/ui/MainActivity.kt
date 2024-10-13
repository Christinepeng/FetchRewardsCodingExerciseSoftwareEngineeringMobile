package com.example.fetchrewardscodingexercisesoftwareengineeringmobile.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fetchrewardscodingexercisesoftwareengineeringmobile.model.ListItem
import com.example.fetchrewardscodingexercisesoftwareengineeringmobile.ui.theme.FetchRewardsCodingExerciseSoftwareEngineeringMobileTheme
import com.example.fetchrewardscodingexercisesoftwareengineeringmobile.viewmodel.FetchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FetchRewardsCodingExerciseSoftwareEngineeringMobileTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FetchListScreen(modifier = Modifier.padding(innerPadding))

                }
            }
        }
    }
}

@Composable
fun FetchListScreen(
    modifier: Modifier = Modifier,
    viewModel: FetchViewModel = hiltViewModel()
) {
    val listItems by viewModel.listItems.collectAsState()

    LazyColumn(modifier = modifier) {
        listItems.groupBy { it.listId }.forEach { (listId, items) ->
            item {
                Text(
                    text = "List ID: $listId",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(8.dp)
                )
            }
            items(items) { listItem ->
                ListItemRow(listItem)
            }
        }
    }
}


@Composable
fun ListItemRow(item: ListItem) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Text(text = item.name ?: "No Name", style = MaterialTheme.typography.bodyLarge)
    }
}