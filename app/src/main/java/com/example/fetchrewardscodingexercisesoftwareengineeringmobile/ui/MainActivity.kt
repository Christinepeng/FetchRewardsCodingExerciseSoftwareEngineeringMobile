package com.example.fetchrewardscodingexercisesoftwareengineeringmobile.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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
                val viewModel: FetchViewModel = hiltViewModel()
                val listItems by viewModel.listItems.collectAsState()
                val errorMessage by viewModel.errorMessage.collectAsState()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FetchListScreen(
                        listItems = listItems,
                        errorMessage = errorMessage,
                        onRetry = { viewModel.fetchListItems() },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun FetchListScreen(
    listItems: List<ListItem>,
    errorMessage: String?,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (errorMessage != null) {
        // Display the error message with a Retry button
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    } else {
        // Display the list with horizontally scrollable rows
        LazyColumn(modifier = modifier) {
            listItems.groupBy { it.listId }.forEach { (listId, itemsForListId) ->
                item {
                    Text(
                        text = "List ID No: $listId",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        items(itemsForListId) { listItem ->
                            ListItemCard(item = listItem)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListItemCard(item: ListItem) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(150.dp), // Adjust the width as needed
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Id: ${item.id}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Name: ${item.name ?: "No Name"}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "List ID: ${item.listId}", style = MaterialTheme.typography.bodySmall)
    }
}
