# Fetch Rewards Coding Exercise - Android App

## Overview

This Android app is developed as a solution to the Fetch Rewards coding exercise. The app fetches and displays a list of items, grouped by their `listId`, with additional functionalities provided via switch buttons. The app follows the **MVVM** architecture and is built using **Jetpack Compose** for the UI.

### Key Features

1. **Display All Items:**
   - When the app is launched, all items are displayed, grouped by `listId`.
   - Items are presented in horizontally scrollable rows for each `listId`.

2. **Two Switch Buttons for Sorting and Filtering:**
   - The app contains two switch buttons that can both be toggled independently:
     - **Sort by Name:** When enabled, the items will be sorted by their `name` within each `listId`.
     - **Filter Blanks:** When enabled, items where `name` is `null` or `""` will be filtered out.
   - **Sort Logic:** When the "Sort by Name" switch is enabled, items are sorted by the numeric part of the `name`. Any items with `null` or empty `name` values are placed at the end.
   - **4 Possible Combinations:**
     - No sorting and no filtering (default view).
     - Sorting by name only.
     - Filtering blank names only.
     - Both sorting and filtering enabled.

3. **Testing:**
   - The app includes comprehensive tests to ensure functionality:
     - **ViewModel Tests** to verify business logic and data handling.
     - **UI Tests** to verify composable rendering and interaction.
     - **Repository Tests** to validate network and data operations.

### Architecture

The app uses the **MVVM** (Model-View-ViewModel) pattern to separate concerns, ensuring better maintainability and testability. The app also leverages **Hilt** for dependency injection, **Retrofit** for network requests, and **Compose** for building UI components.

---

## How to Build and Run the App

1. Clone the repository.
2. Open the project in **Android Studio**.
3. Make sure the project dependencies are up-to-date by syncing the Gradle files.
4. Build and run the app on an emulator or physical device.

By default, the app will display all items grouped by their `listId`.

---

## Features

### Switch 1: Sort by Name
- This switch allows you to sort the items within each `listId` based on their names.
- If names contain numbers, the sorting is done numerically. For example, `Item 2` comes before `Item 12`.
- Any items with an empty `name` (`""`) or `null` are placed at the end.

### Switch 2: Filter Blanks
- When this switch is enabled, any items with `null` or empty `name` values will be excluded from the display.

### 4 Switch Combinations:
1. **No Sorting, No Filtering**: All items are shown in their original order, grouped by `listId`.
2. **Sort by Name**: Items are sorted numerically by their names, with empty or `null` names placed at the end.
3. **Filter Blanks**: Items with `null` or empty names are filtered out, but the original order is maintained.
4. **Sort by Name & Filter Blanks**: Items are sorted by name, and any `null` or empty names are filtered out.

---

## Testing

The project includes a robust suite of tests to ensure the functionality and correctness of the app.

- **ViewModel Tests**: Validate the business logic in `FetchViewModel`.
- **UI Tests**: Verify the proper rendering of composables and interactions with switches.
- **Repository Tests**: Ensure data is fetched and handled correctly from the network.

### Running the Tests
1. In Android Studio, navigate to the **`test`** and **`androidTest`** directories.
2. Right-click on the desired test class and select **Run**.
