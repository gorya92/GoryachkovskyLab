package com.example.kinopoisklab.ui.FilmsDetails

import android.R
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.kinopoisklab.databinding.FragmentFilmsDetailsBinding
import com.example.kinopoisklab.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FilmsDetailsFragment : Fragment() {




    private var binding: FragmentFilmsDetailsBinding by autoCleared()
    private val viewModel: FilmsDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilmsDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt("id")?.let { setupObserver(it) }

        setExitClick()
        setNoInternetButton()

    }
    private fun setExitClick(){
        binding.button.setOnClickListener {

            findNavController().navigate(
                com.example.kinopoisklab.R.id.action_FilmsDetailFragment_to_FilmsFragment,

                )
        }
    }
    private fun setupObserver(id: Int) {
        val connMgr = activity
            ?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo = connMgr.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            binding.noInternetFilms.visibility=GONE
            binding.button.visibility= VISIBLE
            viewModel.getfilm(id).observe(viewLifecycleOwner) { films ->
                var temp: MutableList<String> = mutableListOf()

                films.countries.forEach {
                    temp.add(it.country)
                }

                binding.aboutCountriesTV.text = "Страны: " + temp.joinToString(",")
                temp.clear()
                films.genres.forEach {
                    temp.add(it.genre)
                }

                binding.aboutGenreTV.text = "Жанры: " + temp.joinToString(",")
                Glide.with(binding.root)
                    .load(films.posterUrl)
                    .into(binding.aboutIV)
                binding.aboutTV.text = films.description
                binding.aboutNameTV.text = films.nameRu
            }
        }
        else{
            binding.noInternetFilms.visibility= VISIBLE
            binding.button.visibility= GONE
        }

    }
    private fun setNoInternetButton(){
        binding.retryBtn.setOnClickListener {
            arguments?.getInt("id")?.let { setupObserver(it) }
        }
    }


}