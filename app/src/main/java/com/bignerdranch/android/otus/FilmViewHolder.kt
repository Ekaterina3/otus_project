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
        titleView.text = context.getString(item.title)
        imageView.setImageResource(item.image)

        if (item.isSelected) {
            titleView.setTextColor(ContextCompat.getColor(context, R.color.purple_200))
        } else {
            titleView.setTextColor(ContextCompat.getColor(context, R.color.black))
        }

        if (item.isFavourite) {
            btnFavourites.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            btnFavourites.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }
}