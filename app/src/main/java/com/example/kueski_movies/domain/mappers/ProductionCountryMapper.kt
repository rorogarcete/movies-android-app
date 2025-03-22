package com.example.kueski_movies.domain.mappers

import com.example.kueski_movies.data.remote.model.ProductionCountryResponse
import com.example.kueski_movies.domain.models.ProductionCountry
import com.example.kueski_movies.util.Mapper
import javax.inject.Inject

class ProductionCountryMapper @Inject constructor() : Mapper<ProductionCountryResponse, ProductionCountry> {

    override fun map(input: ProductionCountryResponse) = ProductionCountry(
        iso31661 = input.iso31661,
        name = input.name
    )
}