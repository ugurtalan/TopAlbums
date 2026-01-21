package com.example.top_albums.ui.screens.ListScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.top_albums.domain.model.FilterUiState
import com.example.top_albums.ui.screens.ListScreen.Components.FilterHeader

@Composable
fun ListScreen(
    filterState: FilterUiState,
    onCountrySelected: (String) -> Unit,
    onTypeSelected: (String) -> Unit,
    onTraitSelected: (String) -> Unit,
    onBottomTypeSelected: (String) -> Unit,
    onSearchClicked: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().safeDrawingPadding()) {
        FilterHeader(
            state = filterState,
            onCountrySelected = onCountrySelected,
            onTypeSelected = onTypeSelected,
            onTraitSelected = onTraitSelected,
            onBottomTypeSelected = onBottomTypeSelected,
            onSearchClicked = onSearchClicked
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 2.dp)

        Box(modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp)) {
            content()
        }
    }
}


