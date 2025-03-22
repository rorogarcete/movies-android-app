package com.example.kueski_movies.data.mappers

import com.example.kueski_movies.data.local.entity.MovieEntity
import com.example.kueski_movies.data.remote.model.MovieResponse
import com.example.kueski_movies.util.Mapper
import javax.inject.Inject

class MoviesDataMapper @Inject constructor() : Mapper<Pair<MovieResponse, Int>, MovieEntity> {

    override fun map(input: Pair<MovieResponse, Int>): MovieEntity = MovieEntity(
        id = input.first.id,
        adult = input.first.adult,
        backdropPath = input.first.backdropPath,
        genreIds = input.first.genreIds,
        originalLanguage = input.first.originalLanguage,
        originalTitle = input.first.originalTitle,
        overview = input.first.overview,
        popularity = input.first.popularity,
        posterPath = input.first.posterPath,
        releaseDate = input.first.releaseDate,
        title = input.first.title,
        video = input.first.video,
        voteAverage = input.first.voteAverage,
        voteCount = input.first.voteCount,
        page = input.second
    )
}