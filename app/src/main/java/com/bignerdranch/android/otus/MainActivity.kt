package com.bignerdranch.android.otus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

private const val REQUEST_CODE_DETAILS = 0
private const val KEY_LAST_SELECTED_FILM_INDEX = "last_selected_film_index"
private const val KEY_FILM_WAS_SELECTED = "film_was_selected"

class MainActivity : AppCompatActivity() {
    private val recycler by lazy {
        findViewById<RecyclerView>(R.id.filmsRecycler)
    }
    private var lastSelectedFilmIndex: Int = 0
    private var filmWasSelected: Boolean = false

    private val films = mutableListOf(
        FilmData(R.string.film_1, R.string.description_film_1, R.drawable.film1),
        FilmData(R.string.film_2, R.string.description_film_2, R.drawable.film2),
        FilmData(R.string.film_3, R.string.description_film_3, R.drawable.film3),
        FilmData(R.string.film_1, R.string.description_film_1, R.drawable.film1),
        FilmData(R.string.film_2, R.string.description_film_2, R.drawable.film2),
        FilmData(R.string.film_3, R.string.description_film_3, R.drawable.film3)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecycler()

        filmWasSelected = savedInstanceState?.getBoolean(KEY_FILM_WAS_SELECTED, false) ?: false
        if (filmWasSelected) {
            lastSelectedFilmIndex = savedInstanceState?.getInt(KEY_LAST_SELECTED_FILM_INDEX, 0) ?: 0
            selectFilm(films[lastSelectedFilmIndex], lastSelectedFilmIndex)
        }
    }

    private fun initRecycler () {
        recycler.adapter = FilmsAdapter(films) {
            item, position ->
            run {
                onDetailsBtnClicked(item, position)
            }
        }
    }

    private fun onDetailsBtnClicked (item: FilmData, position: Int) {
        Log.d("check-fl", "click details")
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
}