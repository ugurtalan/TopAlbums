package com.example.top_albums.ui.screens.ListScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.top_albums.data.repository.MainRepo
import com.example.top_albums.domain.model.AlbumUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repo : MainRepo
) : ViewModel() {

    private val _state = MutableStateFlow(AlbumUi())
    val state : StateFlow<AlbumUi> = _state.asStateFlow()

    fun loadAlbums(){
            viewModelScope.launch {
                _state.value = _state.value.copy(isLoading = true)

                val result = repo.getAlbums()


                result.onSuccess{
                    Albums ->
                    Albums.forEach { Log.d("ALBUMT", "${it.name} - ${it.artist}")}
                    _state.value = AlbumUi(
                        isLoading = false,
                        Albums
                    )
                }
                    .onFailure {
                            throwable ->
                        _state.value = AlbumUi(
                            isLoading = false,
                            error = throwable.message )
                    }
            }
    }
}