package com.bignerdranch.android.otus

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FilmsAdapter (
        private val items: List<FilmData>,
        private val clickListener: (item: FilmData, position: Int) -> Unit
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
            clickListener(item, position)
        }
    }

    override fun getItemCount() = items.size
}