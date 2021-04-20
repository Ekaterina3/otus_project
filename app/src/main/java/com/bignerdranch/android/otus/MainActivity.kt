package com.bignerdranch.android.otus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private val films = listOf(
        FilmData(R.string.film_1, R.string.description_film_1, R.drawable.film1),
        FilmData(R.string.film_2, R.string.description_film_2, R.drawable.film2),
        FilmData(R.string.film_3, R.string.description_film_3, R.drawable.film3)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnDetails1).setOnClickListener {
            onDetailsBtnClicked(films[0])
        }
        findViewById<Button>(R.id.btnDetails2).setOnClickListener {
            onDetailsBtnClicked(films[1])
        }
        findViewById<Button>(R.id.btnDetails3).setOnClickListener {
            onDetailsBtnClicked(films[2])
        }
    }

    private fun onDetailsBtnClicked (currentFilm: FilmData) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.FILM_DATA, currentFilm)
        startActivityForResult(intent, 123)
    }
}