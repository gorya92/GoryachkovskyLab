package com.example.kinopoisklab.ui.topfilms

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.kinopoisklab.R
import com.example.kinopoisklab.data.entities.topFilms.Film
import com.example.kinopoisklab.databinding.ItemFilmsBinding

class TopFilmsAdapter(private val listener: TopFilmsItemListener) : RecyclerView.Adapter<TopFilmsViewHolder>() {

    interface TopFilmsItemListener {
        fun onClickedCharacter(characterId: Int)
    }

    private var items = ArrayList<Film>()

    fun filterList(filterList: ArrayList<Film>) {
        items = filterList
        notifyDataSetChanged()
    }

    fun setItems(items: ArrayList<Film>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopFilmsViewHolder {
        val binding: ItemFilmsBinding = ItemFilmsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopFilmsViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TopFilmsViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

class TopFilmsViewHolder(private val itemBinding: ItemFilmsBinding, private val listener: TopFilmsAdapter.TopFilmsItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var film: Film

    init {
        itemBinding.root.setOnClickListener(this)

    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Film) {
        this.film = item
        itemBinding.name.text = item.nameRu

        if (item.favorite){
            itemBinding.star.setImageResource(R.drawable.ic_baseline_activestar_24)
        }
        else{
            itemBinding.star.setImageResource(R.drawable.ic_baseline_star_24)
        }

        Glide.with(itemBinding.root)
            .load(item.posterUrlPreview)
            .transform(RoundedCorners(10))
            .into(itemBinding.image)
        itemBinding.genre.text=  item.genres[0].genre + " (" + item.year + ")"
    }

    override fun onClick(v: View?) {
        listener.onClickedCharacter(film.filmId)
    }
}

