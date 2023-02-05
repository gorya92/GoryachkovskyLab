package com.example.kinopoisklab.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kinopoisklab.data.entities.favorites.FavoriteFilm

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    fun getAllFavorite() : LiveData<List<FavoriteFilm>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteFilm: FavoriteFilm)

    @Query("SELECT * FROM favorite WHERE filmId = :id")
    fun getCharacter(id: Int): LiveData<FavoriteFilm>

    @Delete
    fun delete(favoriteFilm: FavoriteFilm)


}