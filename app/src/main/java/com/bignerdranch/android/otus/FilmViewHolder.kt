package com.bignerdranch.android.otus

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class FilmViewHolder(filmView: View) : RecyclerView.ViewHolder (filmView) {
    private val titleView = filmView.findViewById<TextView>(R.id.title)
    private val imageView = filmView.findViewById<ImageView>(R.id.image)
    private val btnFavourites = filmView.findViewById<ImageView>(R.id.btnFavourites)
    private val context = filmView.context

    fun bind (item: FilmData) {
        titleView.text = item.title
//        imageView.setImageResource(item.image)
        titleView.setTextColor(ContextCompat.getColor(context, if (item.wasVisited) R.color.purple_200 else R.color.black))
        btnFavourites.setImageResource(if (item.isFavourite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24)
    }
}