package com.example.composemoviedb.data.local

import androidx.room.*
import com.example.composemoviedb.data.Movie
import kotlinx.coroutines.flow.Flow

@Database(entities = [LocalMovie::class], version = 1)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}

@Dao
interface MoviesDao {

    @Query("SELECT * FROM LocalMovie")
    fun getMovies(): Flow<List<LocalMovie>>

    @Insert
    suspend fun insertAll(movies: List<LocalMovie>)

    @Update
    suspend fun updateMovie(movie: LocalMovie)

    @Query("SELECT COUNT(*) FROM LocalMovie")
    suspend fun count(): Int

}

@Entity
data class LocalMovie(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val favorite: Boolean = false
)

fun LocalMovie.toMovie() = Movie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    favorite = favorite
)

fun Movie.toLocalMovie() = LocalMovie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    favorite = favorite
)