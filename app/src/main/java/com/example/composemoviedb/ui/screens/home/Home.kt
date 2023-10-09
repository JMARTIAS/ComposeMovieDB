package com.example.composemoviedb.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.composemoviedb.data.Movie
import com.example.composemoviedb.data.MoviesRepository
import com.example.composemoviedb.ui.theme.ComposeMovieDBTheme

@Composable
fun Home(moviesRepository: MoviesRepository) {
    ComposeMovieDBTheme {
        val viewModel: HomeViewModel = viewModel { HomeViewModel(moviesRepository) }
        val state by viewModel.state.collectAsState()

        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                topBar = { TopAppBar(title = { Text(text = "Movies") }) }
            ) { padding ->
                if (state.loading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                if (state.movies.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(120.dp),
                        modifier = Modifier.padding(padding),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        contentPadding = PaddingValues(4.dp)
                    ) {
                        items(state.movies) { movie ->
                            MovieItem(
                                movie = movie,
                                onClick = { viewModel.onMovieClick(movie) }
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun MovieItem(movie: Movie, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w185/${movie.posterPath}",
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2 / 3f)
            )
            if (movie.favorite) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    tint = Color.White
                )
            }
        }

        Text(
            text = movie.title,
            modifier = Modifier
                .padding(16.dp)
                .height(48.dp),
            maxLines = 2
        )
    }
}