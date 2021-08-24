package com.bignerdranch.android.otus

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.recyclerview.widget.RecyclerView

private const val REQUEST_CODE_DETAILS = 0

class FavouritesActivity(
) : MainActivity() {
    private val recycler by lazy {
        findViewById<RecyclerView>(R.id.favsRecycler)
    }
    private var filmsIdList: ArrayList<Int> = ArrayList()
    private var recyclerItems: MutableList<FilmData> = mutableListOf()

    companion object {
        const val FAVOURITES_DATA = "FAVOURITES_DATA"
        const val FAVOURITES_ID_LIST = "FAVOURITES_ID_LIST"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)

        filmsIdList = intent.getParcelableArrayListExtra<Parcelable>(FAVOURITES_ID_LIST) as ArrayList<Int>
        intent.getParcelableArrayListExtra<Parcelable>(FAVOURITES_DATA)?.let {
            recyclerItems = it as MutableList<FilmData>
            initRecycler(recyclerItems)
        }
    }

    private fun initRecycler(films: MutableList<FilmData>) {
        recycler.adapter = FilmsAdapter(films) { item, position, type -> onBtnClicked(type, item, position)
        }
    }

    private fun onBtnClicked(type: String, item: FilmData, position: Int) {
        if (type == KEY_FAVOURITES_BTN) {
            onFavouritesBtnClicked(item, position)
        } else {
            onDetailsBtnClicked(item)
        }
    }

    private fun onFavouritesBtnClicked(item: FilmData, position: Int) {
        filmsIdList.remove(item.id)
        films[item.id - 1].isFavourite = false
        recyclerItems.removeAt(position)
        recycler.adapter?.notifyDataSetChanged()
    }

    private fun onDetailsBtnClicked(item: FilmData) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.FILM_DATA, item)
        startActivityForResult(intent, REQUEST_CODE_DETAILS)
    }
}