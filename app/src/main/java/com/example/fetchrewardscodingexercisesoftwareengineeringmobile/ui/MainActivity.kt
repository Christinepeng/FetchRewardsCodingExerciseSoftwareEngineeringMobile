package com.example.fetchrewardscodingexercisesoftwareengineeringmobile.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    // Remember states for switches
    val isSortByNameEnabled = remember { mutableStateOf(false) }
    val isFilterBlanksEnabled = remember { mutableStateOf(false) }

    if (errorMessage != null) {
        // Display the error message with a Retry button (same as before)
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
        Column(modifier = modifier.fillMaxSize()) {
            // Switches to control sorting and filtering
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp)
            ) {
                // Row to display "Sort by Name" text and switch on the same line
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Switch(
                        checked = isSortByNameEnabled.value,
                        onCheckedChange = { isSortByNameEnabled.value = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.primary)
                    )
                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "Sort by Name",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                // Row to display "Filter Blanks" text and switch on the same line
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Switch(
                        checked = isFilterBlanksEnabled.value,
                        onCheckedChange = { isFilterBlanksEnabled.value = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.primary)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Filter Blanks",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }

            // Process listItems based on the switch states
            var processedItems = listItems

            // Apply filtering if the "Filter Blanks" switch is enabled
            if (isFilterBlanksEnabled.value) {
                processedItems = processedItems.filter { !it.name.isNullOrBlank() }
            }

            // Apply sorting if the "Sort by Name" switch is enabled
            if (isSortByNameEnabled.value) {
                processedItems = processedItems
                    .sortedWith(compareBy<ListItem> { it.listId }.thenBy { extractNumberFromName(it.name) })
            }

            // Display the list with horizontally scrollable rows
            LazyColumn(modifier = Modifier.weight(1f)) {
                processedItems.groupBy { it.listId }.toSortedMap().forEach { (listId, itemsForListId) ->
                    item {
                        Text(
                            text = "List ID No: $listId",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                    }
                    item {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
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
}


@Composable
fun ListItemCard(item: ListItem) {
    Column(
        modifier = Modifier
            .padding(start = 16.dp, top = 8.dp)
            .width(150.dp),
    ) {
        Text(
            text = "Id: ${item.id}",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "List ID: ${item.listId}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            // Display "null" for null values, and "" for empty strings
            text = when {
                item.name == null -> "Name: ${"null"}"
                item.name == "" -> "Name: ${""}"
                else -> "Name: ${item.name}"
            },
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )

    }
}

fun extractNumberFromName(name: String?): Int {
    if (name == null) return Int.MAX_VALUE
    val numberRegex = "\\d+".toRegex()
    val matchResult = numberRegex.find(name)
    return matchResult?.value?.toIntOrNull() ?: Int.MAX_VALUE
}
