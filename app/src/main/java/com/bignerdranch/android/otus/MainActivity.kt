package com.bignerdranch.android.otus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat

private const val REQUEST_CODE_DETAILS = 0
private const val KEY_FILM_INDEX = "film_index"
private const val KEY_FILM_WAS_SELECTED = "film_was_selected"

class MainActivity : AppCompatActivity() {
    private var lastFilmIndex: Int = 0
    private var filmWasSelected: Boolean = false

    private val films = listOf(
        FilmData(R.string.film_1, R.string.description_film_1, R.drawable.film1, R.id.name_film1),
        FilmData(R.string.film_2, R.string.description_film_2, R.drawable.film2, R.id.name_film2),
        FilmData(R.string.film_3, R.string.description_film_3, R.drawable.film3, R.id.name_film3)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        filmWasSelected = savedInstanceState?.getBoolean(KEY_FILM_WAS_SELECTED, false) ?: false
        if (filmWasSelected) {
            lastFilmIndex = savedInstanceState?.getInt(KEY_FILM_INDEX, 0) ?: 0
            selectFilm(lastFilmIndex)
        }
        Log.d("check-fl", "$lastFilmIndex")

        findViewById<Button>(R.id.btnDetails1).setOnClickListener {
            onDetailsBtnClicked(0)
        }
        findViewById<Button>(R.id.btnDetails2).setOnClickListener {
            onDetailsBtnClicked(1)
        }
        findViewById<Button>(R.id.btnDetails3).setOnClickListener {
            onDetailsBtnClicked(2)
        }
    }

    private fun onDetailsBtnClicked (filmIndex: Int) {
        resetSelection()
        selectFilm(filmIndex)

        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.FILM_DATA, films[filmIndex])
        startActivityForResult(intent, REQUEST_CODE_DETAILS)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_FILM_INDEX, lastFilmIndex)
        outState.putBoolean(KEY_FILM_WAS_SELECTED, filmWasSelected)
    }

    private fun resetSelection() {
        val lastFilm = films[lastFilmIndex]
        findViewById<TextView>(lastFilm.nameId).setTextColor(ContextCompat.getColor(this, R.color.black))
    }

    private fun selectFilm(filmIndex: Int) {
        val currentFilm = films[filmIndex]
        findViewById<TextView>(currentFilm.nameId).setTextColor(ContextCompat.getColor(this, R.color.purple_200))
        lastFilmIndex = filmIndex

        if (!filmWasSelected) {
            filmWasSelected = true
        }
    }
}