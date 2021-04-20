package com.bignerdranch.android.otus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView

class DetailsActivity: AppCompatActivity() {
    companion object {
        const val FILM_DATA = "FILM_DATA"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        intent.getParcelableExtra<FilmData>(FILM_DATA)?.let {
            findViewById<TextView>(R.id.titleView).text = getString(it.name)
            findViewById<TextView>(R.id.descriptionView).text = getString(it.description)
            findViewById<ImageView>(R.id.imageView).setImageResource(it.image)
        }
    }
}