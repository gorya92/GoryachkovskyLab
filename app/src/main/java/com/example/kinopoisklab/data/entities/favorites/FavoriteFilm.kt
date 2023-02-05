package com.example.kinopoisklab.data.entities.favorites

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kinopoisklab.data.entities.topFilms.Country
import com.example.kinopoisklab.data.entities.topFilms.Genre
@Entity(tableName = "favorite")
data class FavoriteFilm(
    val countrie: String,
    @PrimaryKey
    val filmId: Int,
    val genres: String,
    val nameRu: String,
    val posterUrlPreview: String,
    val year: String
)