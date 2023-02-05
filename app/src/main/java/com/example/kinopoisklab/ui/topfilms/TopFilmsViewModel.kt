package com.example.kinopoisklab.ui.topfilms

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.kinopoisklab.data.entities.topFilms.Film
import com.example.kinopoisklab.data.repository.FilmRepository

class TopFilmsViewModel @ViewModelInject constructor(
    private val repository: FilmRepository
) : ViewModel() {

    fun getTop() : LiveData<List<Film>> {
        return liveData {
            var data = repository.getTopFilms(1).body()?.films as MutableList
            for (i in 2 until 6) {
               data.addAll(repository.getTopFilms(i).body()?.films as MutableList)
            }

            emit(data)
        }
    }

}