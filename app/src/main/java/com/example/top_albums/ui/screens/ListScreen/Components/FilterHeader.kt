package com.example.top_albums.ui.screens.ListScreen.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.top_albums.domain.model.FilterUiState

@Composable
 fun FilterHeader(
    state: FilterUiState,
    onCountrySelected: (String) -> Unit,
    onTypeSelected: (String) -> Unit,
    onTraitSelected: (String) -> Unit,
    onBottomTypeSelected: (String) -> Unit,
    onSearchClicked: () -> Unit
) {
    Column(
        modifier = Modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            MyDropdown(
                label = "Country",
                options = listOf("Türkiye", "Argentina", "Ukraine", "Belgium", "Azerbaijan"),
                selectedOption = state.country,
                onOptionSelected = onCountrySelected,
                modifier = Modifier.weight(1f)
            )
            MyDropdown(
                label = "Type",
                options = listOf("Music", "Apps", "Books"),
                selectedOption = state.type,
                onOptionSelected = onTypeSelected,
                modifier = Modifier.weight(1f)
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            MyDropdown(
                label = "Trait",
                options = state.traitOptions,
                selectedOption = state.trait,
                onOptionSelected = onTraitSelected,
                modifier = Modifier.weight(1f)
            )
            MyDropdown(
                label = "Bottom Type",
                options = state.bottomTypeOptions,
                selectedOption = state.bottomType,
                onOptionSelected = onBottomTypeSelected,
                modifier = Modifier.weight(1f)
            )
        }

        Button(
            onClick = onSearchClicked,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Icon(Icons.Default.Search, contentDescription = null)
            Spacer(Modifier.width(8.dp))
        }
    }
}
