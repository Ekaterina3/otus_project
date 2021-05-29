package com.bignerdranch.android.otus

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class FavouritesActivity(
) : MainActivity() {
    private val recycler by lazy {
        findViewById<RecyclerView>(R.id.favsRecycler)
    }

    companion object {
        const val FAVOURITES_DATA = "FAVOURITES_DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)
        intent.getParcelableArrayListExtra<Parcelable>(FAVOURITES_DATA)?.let {
            initRecycler(it as MutableList<FilmData>)
        }
    }

    private fun initRecycler(films: MutableList<FilmData>) {
        recycler.adapter = FilmsAdapter(films) { item, position, type -> onBtnClicked(type, item, position)
        }
    }
}