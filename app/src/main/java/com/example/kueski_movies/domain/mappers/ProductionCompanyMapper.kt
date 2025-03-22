package com.example.kueski_movies.domain.mappers

import com.example.kueski_movies.data.remote.model.ProductionCompanyResponse
import com.example.kueski_movies.domain.models.ProductionCompany
import com.example.kueski_movies.util.Mapper
import javax.inject.Inject

class ProductionCompanyMapper @Inject constructor() : Mapper<ProductionCompanyResponse, ProductionCompany> {

    override fun map(input: ProductionCompanyResponse) = ProductionCompany(
        id = input.id,
        logoPath = input.logoPath,
        name = input.name,
        originCountry = input.originCountry
    )
}