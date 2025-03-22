package com.example.kueski_movies.domain.mappers

import com.example.kueski_movies.data.remote.model.MovieResponse
import com.example.kueski_movies.domain.models.Movie
import com.example.kueski_movies.util.Mapper
import javax.inject.Inject

class MovieMapper @Inject constructor() : Mapper<MovieResponse, Movie> {

    override fun map(input: MovieResponse) = Movie(
        id = input.id,
        adult = input.adult,
        backdropPath = input.backdropPath,
        genreIds = input.genreIds,
        originalLanguage = input.originalLanguage,
        originalTitle = input.originalTitle,
        overview = input.overview,
        popularity = input.popularity,
        posterPath = input.posterPath,
        releaseDate = input.releaseDate,
        title = input.title,
        video = input.video,
        voteAverage = input.voteAverage,
        voteCount = input.voteCount,
    )
}