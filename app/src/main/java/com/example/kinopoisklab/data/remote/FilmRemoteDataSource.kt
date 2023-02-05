package com.example.kinopoisklab.data.remote

import javax.inject.Inject

class FilmRemoteDataSource @Inject constructor(
    private val filmService: FilmService
){

    suspend fun getTopFilms(i : Int) =  filmService.getAllFilms(
        "TOP_100_POPULAR_FILMS",i)

    suspend fun getFilm(id: Int) =  filmService.getFilm(id)
}