package com.bignerdranch.android.otus

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class FilmViewHolder(filmView: View) : RecyclerView.ViewHolder (filmView) {
    private val titleView = filmView.findViewById<TextView>(R.id.title)
    private val imageView = filmView.findViewById<ImageView>(R.id.image)
    private val context = filmView.context

    fun bind (item: FilmData) {
        titleView.text = context.getString(item.title)
        imageView.setImageResource(item.image)

        if (item.isSelected) {
            titleView.setTextColor(ContextCompat.getColor(context, R.color.purple_200))
        } else {
            titleView.setTextColor(ContextCompat.getColor(context, R.color.black))
        }
    }
}