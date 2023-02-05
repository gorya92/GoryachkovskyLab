package com.example.kinopoisklab.data.remote

import com.example.kinopoisklab.data.entities.aboutFilm.AboutFilm
import com.example.kinopoisklab.data.entities.topFilms.Films
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmService {
    @GET("films/top")
    suspend fun getAllFilms(
        @Query("type") type: String,
        @Query("page") page: Int
    ): Response<Films>

    @GET("films/{id}")
    suspend fun getFilm(
        @Path("id") id: Int
    ): Response<AboutFilm>

}