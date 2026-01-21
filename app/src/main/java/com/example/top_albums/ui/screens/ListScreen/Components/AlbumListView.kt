package com.example.top_albums.ui.screens.ListScreen.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.top_albums.domain.model.MyAlbum

@Composable
fun AlbumListView(albums: List<MyAlbum>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        itemsIndexed(albums) { index, album ->
            AlbumCard(album = album, sequence = index + 1)
        }
    }
}
