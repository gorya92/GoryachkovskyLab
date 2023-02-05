package com.example.kinopoisklab.data.repository

import android.util.Log
import com.example.kinopoisklab.data.entities.favorites.FavoriteFilm
import com.example.kinopoisklab.data.local.FavoriteDao
import com.example.kinopoisklab.data.remote.FilmRemoteDataSource
import javax.inject.Inject

class FilmRepository @Inject constructor(
    private val remoteDataSource: FilmRemoteDataSource,
    private val localDataSource: FavoriteDao
) {


    suspend fun getTopFilms(i : Int) =  remoteDataSource.getTopFilms(i)

    suspend fun getFilm(id : Int) = remoteDataSource.getFilm(id)

    suspend fun putInFavorite(favoriteFilm: FavoriteFilm) = localDataSource.insert(favoriteFilm)

    suspend fun  getAllFavorite() = localDataSource.getAllFavorite()

    suspend fun  checkFavorite(id : Int): Boolean {
      val a =   localDataSource.getCharacter(id)

        return a.value != null
    }

    suspend fun delete(favoriteFilm: FavoriteFilm) = localDataSource.delete(favoriteFilm)
}