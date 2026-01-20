package com.example.top_albums.ui.screens.ListScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.top_albums.domain.model.Choices



@Composable
fun ListScreen(
    viewModel: ListViewModel = hiltViewModel()
){

    val state by viewModel.state.collectAsState()
    var choicesState by remember { mutableStateOf<Choices>(Choices()) }
    var searchState by remember { mutableStateOf(false) }

    LaunchedEffect(searchState , Unit) {

        viewModel.loadAlbums(choicesState.country,choicesState.type.lowercase(),choicesState.trait.lowercase(),choicesState.bottomType.lowercase())
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


                Column(
                    modifier = Modifier.padding(horizontal = 12.dp).safeDrawingPadding(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {

                    Row(modifier = Modifier.fillMaxWidth()) {
                        MyDropdown(
                            label = "Country",
                            options = listOf("Türkiye", "Argentina", "Ukraine", "Belgium" ,"Azerbaijan"),
                            selectedOption = choicesState.country,
                            onOptionSelected = {
                                choicesState = choicesState.copy(country = it)
                            },
                            modifier = Modifier.weight(1f)
                        )
                        MyDropdown(
                            label = "Type",
                            options = listOf("Music", "Apps", "Books"),
                            selectedOption = choicesState.type,
                            onOptionSelected = {
                                choicesState = choicesState.copy(
                                    type = it,
                                    trait = when (it) {
                                        "Music" -> "Most-Played"
                                        "Apps", "Books" -> "Top-Paid"
                                        else -> ""
                                    },
                                    bottomType = when (it){
                                        "Music"-> "Albums"
                                        "Apps" ->"Apps"
                                        "Books" -> "Books"
                                        else ->    ""
                                    }




                                )
                                               },
                            modifier = Modifier.weight(1f)
                        )
                    }


                    Row(modifier = Modifier.fillMaxWidth()) {
                        MyDropdown(
                            label = "Trait",
                            options = if (choicesState.type=="Apps" || choicesState.type=="Books") listOf("Top-Free","Top-Paid") else listOf("Most-Played"),
                            selectedOption = choicesState.trait,
                            onOptionSelected = { choicesState = choicesState.copy(trait = it) },
                            modifier = Modifier.weight(1f)
                        )
                        MyDropdown(
                            label = "Bottom Type",
                            options = if(choicesState.type =="Music") listOf("Albums" , "Songs") else listOf(""),
                            selectedOption = choicesState.bottomType,
                            onOptionSelected = {choicesState = choicesState.copy(
                                bottomType = it
                            ) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(modifier =Modifier.fillMaxWidth() ) {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 12.dp),
                            onClick = {searchState = !searchState}) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon",
                                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(12.dp)
                            )
                        }
                    }


                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(4.dp),

                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        itemsIndexed(state.albums) { index,album ->
                            AlbumCard(album,index+1)
                        }
                    }
                }
                }


            else ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterVertically),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("No data found" ,style = MaterialTheme.typography.titleLarge)
                    Button(onClick = {
                        choicesState = Choices("Türkiye","Music","Most-Played", "Albums")
                        viewModel.loadAlbums("Türkiye","music","most-played", "albums")})
                    {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh Icon",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

        }
    }

    Log.d("APİ CALL" , "${choicesState.type},${choicesState.country},${choicesState.trait},${choicesState.bottomType}")
}






