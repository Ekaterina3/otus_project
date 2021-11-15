package com.bignerdranch.android.otus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

open class MainActivity : AppCompatActivity() {
    private val bottomNavigationView: BottomNavigationView by lazy {
        findViewById(R.id.bottomNavigation)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        showFilmsList()
        setBottomNavigationClickListener()
    }

    private fun setBottomNavigationClickListener() {
        bottomNavigationView.setOnItemReselectedListener {}
        bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_home -> {
                    showFilmsList()
                }
                R.id.nav_favourites -> {
                    showFavouritesList()
                }
            }
            true
        }
    }

    private fun showFilmsList() {
        val fragment = FilmsListFragment.newInstance(films as ArrayList<FilmData>)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment, FilmsListFragment.TAG)
            .commit()
    }

    private fun showFavouritesList() {
        val favouritesList: List<FilmData> = films.filter {
            it.isFavourite
        }

        val fragment = FavouriteFilmsListFragment.newInstance(favouritesList as ArrayList<FilmData>)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment, FavouriteFilmsListFragment.TAG)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            CustomDialog().show(supportFragmentManager, TAG_DIALOG)
        }
    }

    companion object {
        const val TAG_DIALOG = "dialog"

        val films = mutableListOf(
            FilmData(R.string.film_1, R.string.description_film_1, R.drawable.film1, id = 1, isFavourite = true),
            FilmData(R.string.film_2, R.string.description_film_2, R.drawable.film2, id = 2),
            FilmData(R.string.film_3, R.string.description_film_3, R.drawable.film3, id = 3),
            FilmData(R.string.film_1, R.string.description_film_1, R.drawable.film1, id = 4),
            FilmData(R.string.film_2, R.string.description_film_2, R.drawable.film2, id = 5),
            FilmData(R.string.film_3, R.string.description_film_3, R.drawable.film3, id = 6)
        )
    }
}