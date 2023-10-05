package com.example.composemoviedb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.composemoviedb.ui.theme.ComposeMovieDBTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMovieDBTheme {

                val viewModel : MainViewModel = viewModel()
                val state by viewModel.state.collectAsState()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = { TopAppBar(title = { Text(text = "Movies") }) }
                    ) { padding ->
                        if(state.loading){
                            Box(
                                modifier= Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ){
                                CircularProgressIndicator()
                            }

                        }
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(120.dp),
                            modifier = Modifier.padding(padding),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            contentPadding = PaddingValues(4.dp)
                        ) {
                            items(state.movies) { movie ->
                                Column {
                                    MovieItem(movie) { viewModel.onMovieClick(movie) }
                                }
                            }
                        }

                    }

                }
            }
        }
    }

    @Composable
    fun MovieItem(movie: ServerMovie, onClick:() -> Unit) {
        Column(
            modifier= Modifier.clickable(onClick = onClick)
        ) {
            Box{
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w185/${movie.poster_path}",
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f / 3f)
                )

                if (movie.favorite){
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = movie.title,
                        modifier = Modifier.align(Alignment.TopEnd).padding(8.dp),
                        tint = Color.Red
                    )
                }
            }


            Text(
                text = movie.title,
                modifier = Modifier.padding(8.dp), maxLines = 2
            )
        }
    }

    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!")
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        ComposeMovieDBTheme {
            Greeting("Android")
        }
    }
}