package com.example.kinopoisklab.ui.topfilms

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kinopoisklab.R
import com.example.kinopoisklab.data.entities.topFilms.Film
import com.example.kinopoisklab.databinding.FragmentTopFilmsBinding
import com.example.kinopoisklab.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class TopFilmsFragment : Fragment(), TopFilmsAdapter.TopFilmsItemListener, SearchView.OnQueryTextListener {

    private var binding: FragmentTopFilmsBinding by autoCleared()
    private val viewModel: TopFilmsViewModel by viewModels()
    private lateinit var adapter: TopFilmsAdapter



    lateinit var data : ArrayList<Film>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopFilmsBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }



    private fun filter(text: String) {
        val filteredlist = ArrayList<Film>()

        for (item in data) {
            if (item.nameRu.toLowerCase().contains(text.lowercase(Locale.getDefault()))) {
                filteredlist.add(item)
            }
            if (filteredlist.isEmpty()) {
            } else {
                adapter.filterList(filteredlist)
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setNoInternetButton()
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbar.toolbar)
    }

    private fun setupRecyclerView() {
        adapter = TopFilmsAdapter(this)
        binding.topFilmsRv.layoutManager = LinearLayoutManager(requireContext())
        binding.topFilmsRv.adapter = adapter
    }

    private fun setupObservers() {
        binding.progressBar.visibility= VISIBLE
        val connMgr = activity
            ?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo = connMgr.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            binding.noInternetFilms.visibility= GONE
            if (com.example.kinopoisklab.utils.Object.data.isEmpty())
                viewModel.getTop().observe(viewLifecycleOwner) { films ->
                    adapter.setItems(films as ArrayList<Film>)
                    com.example.kinopoisklab.utils.Object.data= films
                    data = films
                    adapter.setItems(data)
                    binding.progressBar.visibility= GONE
                }
            else{
                adapter.setItems(com.example.kinopoisklab.utils.Object.data as ArrayList<Film>)
                binding.progressBar.visibility= GONE
            }

        } else {
            binding.progressBar.visibility= GONE
            if (com.example.kinopoisklab.utils.Object.data.isNotEmpty()){
                adapter.setItems(com.example.kinopoisklab.utils.Object.data as ArrayList<Film>)
            }
            else{
                binding.noInternetFilms.visibility= VISIBLE
            }
        }


    }
    private fun setNoInternetButton(){
        binding.retryBtn.setOnClickListener {
            setupObservers()
        }
    }

    override fun onClickedCharacter(characterId: Int) {
        val bundle = Bundle()
        bundle.putInt("id" , characterId)
        findNavController().navigate(
            com.example.kinopoisklab.R.id.action_FilmsFragment_to_FilmsDetailFragment,
            bundle
        )
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item, menu)
        var menuItem : MenuItem = menu.findItem(R.id.action_search)
        var searchView : SearchView = MenuItemCompat.getActionView(menuItem) as SearchView
        searchView.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onQueryTextSubmit(p0: String?): Boolean {
return   false
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        if (p0 != null) {
            filter(p0)
        }
return false
    }

}