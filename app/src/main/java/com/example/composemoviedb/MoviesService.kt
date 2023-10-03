package com.example.composemoviedb

import retrofit2.http.GET

interface MoviesService {
    @GET("discover/movie?api_key=0296e202444fceb2265dc3b00ab3f282")
    suspend fun getMovies(): MovieResult

}