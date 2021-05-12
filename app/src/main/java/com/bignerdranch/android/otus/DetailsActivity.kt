package com.bignerdranch.android.otus

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class DetailsActivity: AppCompatActivity() {
    companion object {
        const val FILM_DATA = "FILM_DATA"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        intent.getParcelableExtra<FilmData>(FILM_DATA)?.let { film ->
            findViewById<TextView>(R.id.titleView).text = getString(film.name)
            findViewById<TextView>(R.id.descriptionView).text = getString(film.description)
            findViewById<ImageView>(R.id.imageView).setImageResource(film.image)

            findViewById<Button>(R.id.inviteButton).setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "Интересный фильм, советую посмотреть: ${getString(film.name)}.")
                }

                try {
                    startActivity(Intent.createChooser(intent, "Send message with..."))
                } catch (ex: ActivityNotFoundException) {
                    Toast.makeText(
                        this,
                        "There are no suitable clients installed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}