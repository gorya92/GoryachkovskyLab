package com.example.kinopoisklab.ui.FilmsDetails


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.kinopoisklab.data.entities.aboutFilm.AboutFilm
import com.example.kinopoisklab.data.entities.topFilms.Film
import com.example.kinopoisklab.data.repository.FilmRepository

    class FilmsDetailsViewModel @ViewModelInject constructor(
    private val repository: FilmRepository
) : ViewModel() {

    fun getfilm(id : Int) : LiveData<AboutFilm> {
        return liveData {
            var data = repository.getFilm(id).body()

            data?.let { emit(it) }
        }
    }

}