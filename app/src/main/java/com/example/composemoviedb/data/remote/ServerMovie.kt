package com.example.composemoviedb.data.remote

import com.example.composemoviedb.data.Movie
import com.example.composemoviedb.data.local.LocalMovie

data class ServerMovie(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    val favorite: Boolean = false
)

fun ServerMovie.toMovie() = Movie(
    id = 0,
    title = title,
    overview = overview,
    posterPath = poster_path,
    favorite = favorite
)

fun ServerMovie.toLocalMovie() = LocalMovie(
    id = 0,
    title = title,
    overview = overview,
    favorite = favorite,
    posterPath = poster_path
)