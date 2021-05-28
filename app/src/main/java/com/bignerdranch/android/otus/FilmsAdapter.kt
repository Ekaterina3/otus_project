package com.bignerdranch.android.otus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.otus.MainActivity.Companion.KEY_DETAILS_BTN
import com.bignerdranch.android.otus.MainActivity.Companion.KEY_FAVOURITES_BTN

class FilmsAdapter (
        private val items: List<FilmData>,
        private val clickListener: (item: FilmData, position: Int, clickType: String) -> Unit
): RecyclerView.Adapter<FilmViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_film, parent, false)
        return FilmViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        val detailsButton = holder.itemView.findViewById<View>(R.id.btnDetails)
        detailsButton.setOnClickListener {
            clickListener(item, position, KEY_DETAILS_BTN)
        }

        val favouriteButton = holder.itemView.findViewById<View>(R.id.btnFavourites)
        favouriteButton.setOnClickListener {
            clickListener(item, position, KEY_FAVOURITES_BTN)
        }
    }

    override fun getItemCount() = items.size
}