package com.example.kueski_movies.domain.mappers

import com.example.kueski_movies.data.remote.model.MovieDetailsResponse
import com.example.kueski_movies.domain.models.MovieDetail
import com.example.kueski_movies.util.Mapper
import javax.inject.Inject

class MovieDetailMapper @Inject constructor(
    private val productionCompanyMapper: ProductionCompanyMapper,
    private val productionCountryMapper: ProductionCountryMapper
) : Mapper<MovieDetailsResponse, MovieDetail> {

    override fun map(input: MovieDetailsResponse) = MovieDetail(
        id = input.id,
        title = input.title,
        overview = input.overview,
        releaseDate = input.releaseDate,
        posterPath = input.posterPath,
        voteAverage = input.voteAverage,
        productionCompanies = input.productionCompanies.map {
            productionCompanyMapper.map(it)
        },
        productionCountries = input.productionCountries.map {
            productionCountryMapper.map(it)
        }
    )
}