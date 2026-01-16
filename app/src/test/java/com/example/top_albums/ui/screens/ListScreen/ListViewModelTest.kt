package com.example.top_albums.ui.screens.ListScreen


import com.example.top_albums.data.repository.MainRepo
import com.example.top_albums.domain.model.MyAlbum
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelTest {

    private lateinit var viewModel: ListViewModel
    private val repo: MainRepo = mockk() // Repo'nun sahtesini oluşturuyoruz
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // ViewModel'deki viewModelScope'un test sırasında hata vermemesi için
        Dispatchers.setMain(testDispatcher)
        viewModel = ListViewModel(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadAlbums success should update state and cache data`() = runTest {
        // 1. GIVEN: Repo çağrıldığında dönecek sahte veriyi hazırla
        val fakeAlbums = listOf(MyAlbum("Album 1", "Artist 1", "1", "", ""))
        coEvery {
            repo.getAlbums(any(), any(), any(), any())
        } returns Result.success(fakeAlbums)

        // 2. WHEN: Metodu çağır
        viewModel.loadAlbums("tr", "music", "most-played", "albums")

        // Coroutine'lerin bitmesini bekle
        advanceUntilIdle()

        // 3. THEN: State'in güncellendiğini doğrula
        assertEquals(fakeAlbums, viewModel.state.value.albums)
        assertEquals(false, viewModel.state.value.isLoading)

        // Repo'nun gerçekten çağrıldığından emin ol
        coVerify(exactly = 1) { repo.getAlbums("tr", "music", "most-played", "albums") }
    }

    @Test
    fun `loadAlbums should return data from cache on second call`() = runTest {
        // GIVEN: Repo sadece bir kez başarılı dönsün
        val fakeAlbums = listOf(MyAlbum("Album 1", "Artist 1", "1", "", ""))
        coEvery { repo.getAlbums(any(), any(), any(), any()) } returns Result.success(fakeAlbums)

        // WHEN: İlk çağrı (Veri internetten çekilir)
        viewModel.loadAlbums("tr", "music", "most-played", "albums")
        advanceUntilIdle()

        // İkinci çağrı (Aynı parametrelerle - Veri cache'den gelmeli)
        viewModel.loadAlbums("tr", "music", "most-played", "albums")
        advanceUntilIdle()

        // THEN: Repo toplamda SADECE 1 KEZ çağrılmış olmalı (ikinci kez çağrılmamalı)
        coVerify(exactly = 1) { repo.getAlbums(any(), any(), any(), any()) }
        assertEquals(fakeAlbums, viewModel.state.value.albums)
    }
}