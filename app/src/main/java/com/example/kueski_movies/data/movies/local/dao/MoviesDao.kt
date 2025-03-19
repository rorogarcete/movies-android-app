package  com.example.kueski_movies.data.movies.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kueski_movies.data.movies.local.entity.MovieEntity

@Dao
interface MoviesDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(movies: List<MovieEntity>)

  @Query("SELECT * FROM movies ORDER BY page")
  fun getMovies(): PagingSource<Int, MovieEntity>

  @Query("DELETE FROM movies")
  suspend fun deleteAll()
}