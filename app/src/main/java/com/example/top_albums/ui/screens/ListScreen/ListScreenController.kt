package com.example.top_albums.ui.screens.ListScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.top_albums.ui.screens.ListScreen.Components.AlbumListView
import com.example.top_albums.ui.screens.ListScreen.Components.EmptyView
import com.example.top_albums.ui.screens.ListScreen.Components.ErrorView

@Composable
fun ListScreenController(
    viewModel: ListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val filterState by viewModel.filterState.collectAsState()

    val content: @Composable () -> Unit = {
        when {
            uiState.isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            uiState.error != null -> {
                ErrorView(message = uiState.error?:"Some Error Occured", onRetry = { viewModel.onSearchClicked() })
            }
            uiState.albums.isNotEmpty() -> {
                AlbumListView(albums = uiState.albums)
            }
            else -> {
                EmptyView(onAction = { viewModel.onSearchClicked() })
            }
        }
    }

    ListScreen(
        filterState = filterState,
        onCountrySelected = { viewModel.onCountrySelected(it) },
        onTypeSelected = { viewModel.onTypeSelected(it) },
        onTraitSelected = { viewModel.onTraitSelected(it) },
        onBottomTypeSelected = { viewModel.onBottomTypeSelected(it) },
        onSearchClicked = { viewModel.onSearchClicked() },
        content = content
    )
}