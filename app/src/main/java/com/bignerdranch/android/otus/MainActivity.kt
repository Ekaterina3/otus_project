package com.bignerdranch.android.otus

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

private const val REQUEST_CODE_DETAILS = 0
private const val REQUEST_CODE_FAVOURITES = 1
private const val KEY_LAST_SELECTED_FILM_INDEX = "last_selected_film_index"
private const val KEY_FILM_WAS_SELECTED = "film_was_selected"
private const val KEY_FAVOURITES_LIST = "favourites_list"

open class MainActivity : AppCompatActivity() {
    private val recycler by lazy {
        findViewById<RecyclerView>(R.id.filmsRecycler)
    }
    private var lastSelectedFilmIndex: Int = 0
    private var filmWasSelected: Boolean = false
    private var favouritesIdList: ArrayList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecycler()

        // check last selected element
        filmWasSelected = savedInstanceState?.getBoolean(KEY_FILM_WAS_SELECTED, false) ?: false
        if (filmWasSelected) {
            lastSelectedFilmIndex = savedInstanceState?.getInt(KEY_LAST_SELECTED_FILM_INDEX, 0) ?: 0
            selectFilm(films[lastSelectedFilmIndex], lastSelectedFilmIndex)
        }

        // check favourites list
        favouritesIdList = savedInstanceState?.getIntegerArrayList(KEY_FAVOURITES_LIST) ?: arrayListOf()
        if (favouritesIdList.size > 0) {
            favouritesIdList.forEach {
                films[it - 1].isFavourite = true
            }
            recycler.adapter?.notifyDataSetChanged()
        }

        findViewById<TextView>(R.id.showFavourites).setOnClickListener {
            showFavouritesActivity()
        }
    }

    private fun initRecycler () {
        recycler.adapter = FilmsAdapter(films) { item, position, type -> onBtnClicked(type, item, position)
        }
    }

    private fun onBtnClicked(type: String, item: FilmData, position: Int) {
        if (type == KEY_FAVOURITES_BTN) {
            onFavouritesBtnClicked(item, position)
        } else {
            onDetailsBtnClicked(item, position)
        }
    }

    private fun onFavouritesBtnClicked(item: FilmData, position: Int) {
        if (item.isFavourite) {
            favouritesIdList.remove(item.id)
        } else {
            favouritesIdList.add(item.id)
        }
        item.isFavourite = !item.isFavourite
        recycler.adapter?.notifyItemChanged(position)
    }

    private fun onDetailsBtnClicked(item: FilmData, position: Int) {
        resetSelection()
        selectFilm(item, position)

        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.FILM_DATA, item)
        startActivityForResult(intent, REQUEST_CODE_DETAILS)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_LAST_SELECTED_FILM_INDEX, lastSelectedFilmIndex)
        outState.putBoolean(KEY_FILM_WAS_SELECTED, filmWasSelected)
        outState.putIntegerArrayList(KEY_FAVOURITES_LIST, favouritesIdList)
    }

    private fun resetSelection() {
        films[lastSelectedFilmIndex].isSelected = false
        recycler.adapter?.notifyItemChanged(lastSelectedFilmIndex)
    }

    private fun selectFilm(item: FilmData, position: Int) {
        item.isSelected = true
        recycler.adapter?.notifyItemChanged(position)
        lastSelectedFilmIndex = position

        if (!filmWasSelected) {
            filmWasSelected = true
        }
    }

    private fun showFavouritesActivity() {
        val favouritesList: List<FilmData> = films.filter {
            it.isFavourite
        }

        val intent = Intent(this, FavouritesActivity::class.java)
        intent.putExtra(FavouritesActivity.FAVOURITES_DATA, ArrayList<FilmData>(favouritesList))
        intent.putExtra(FavouritesActivity.FAVOURITES_ID_LIST, ArrayList<Int>(favouritesIdList))
        startActivityForResult(intent, REQUEST_CODE_FAVOURITES)
    }

    // после возвращения с экрана избранного актуализируем список избранного на главном экране
    override fun onResume() {
        super.onResume()
        recycler.adapter?.notifyDataSetChanged()
    }

    companion object {
        const val KEY_FAVOURITES_BTN = "favourites_btn"
        const val KEY_DETAILS_BTN = "details_btn"

        val films = mutableListOf(
                FilmData(R.string.film_1, R.string.description_film_1, R.drawable.film1, id = 1),
                FilmData(R.string.film_2, R.string.description_film_2, R.drawable.film2, id = 2),
                FilmData(R.string.film_3, R.string.description_film_3, R.drawable.film3, id = 3),
                FilmData(R.string.film_1, R.string.description_film_1, R.drawable.film1, id = 4),
                FilmData(R.string.film_2, R.string.description_film_2, R.drawable.film2, id = 5),
                FilmData(R.string.film_3, R.string.description_film_3, R.drawable.film3, id = 6)
        )
    }
}