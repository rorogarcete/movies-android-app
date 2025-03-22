package com.example.kueski_movies.util


interface Mapper<I, O> {
    fun map(input: I): O
}