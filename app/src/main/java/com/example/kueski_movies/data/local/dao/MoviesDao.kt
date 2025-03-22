package  com.example.kueski_movies.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kueski_movies.data.local.entity.MovieEntity

@Dao
interface MoviesDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(movies: List<MovieEntity>)

  @Query("SELECT * FROM movies ORDER BY page")
  fun getMovies(): List<MovieEntity>

  @Query("DELETE FROM movies")
  suspend fun deleteAll()
}