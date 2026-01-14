package com.example.top_albums.ui.screens.ListScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.top_albums.domain.model.AlbumUi
import com.example.top_albums.domain.model.MyAlbum
import coil.compose.AsyncImage


@Composable
fun ListScreen(
    viewModel: ListViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadAlbums()
    }

    Column(modifier = Modifier.fillMaxSize()) {



        when {
            state.isLoading ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterVertically),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Loading...", style = MaterialTheme.typography.titleLarge)
                    CircularProgressIndicator()
                }

            state.error != null ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterVertically),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Error: ${state.error}" ,style = MaterialTheme.typography.titleLarge)
                    CircularProgressIndicator()
                }

            state.albums.isNotEmpty() -> {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(32.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.albums) { album ->
                        AlbumCard(album)
                    }
                }
            }
            else ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterVertically),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("No albums found" ,style = MaterialTheme.typography.titleLarge)
                    CircularProgressIndicator()
                }

        }
    }
}

@Composable
@Preview
fun ListScreenPreview(){
    val state = AlbumUi(
        isLoading = false,
        albums = listOf<MyAlbum>(
            MyAlbum(
                name = "Album 1", artist = "Artist 1",
                id = "",
                imageUrl = "",
                appleUrl = ""
            ),
            MyAlbum(
                name = "Album 2", artist = "Artist 2",
                id = "",
                imageUrl = "",
                appleUrl = ""
            ),
        ),
        error = null
    )


    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(state.albums) { album ->
            AlbumCard(album)
        }
    }


}



@Composable
fun AlbumCard(album: MyAlbum) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),

        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = album.imageUrl,
                contentDescription = "",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),

            )

            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(
                    text = album.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = album.artist,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
