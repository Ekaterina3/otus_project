package com.bignerdranch.android.otus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class MainActivity : AppCompatActivity() {
    private val bottomNavigationView: BottomNavigationView by lazy {
        findViewById(R.id.bottomNavigation)
    }

    var films = mutableListOf<FilmData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setBottomNavigationClickListener()

        App.instance.api.getFilms()
            .enqueue(object : Callback<List<ResponseModel>?> {
                override fun onFailure(call: Call<List<ResponseModel>?>, t: Throwable) {
                }
                override fun onResponse(
                    call: Call<List<ResponseModel>?>,
                    response: Response<List<ResponseModel>?>
                ) {
                    if (response.isSuccessful) {
                        films.clear()
                        response.body()
                            ?.forEach {
                                films.add(
                                    FilmData(
                                        it.title,
                                        null,
                                        it.image,
                                        wasVisited = false,
                                        isFavourite = false,
                                        id = it.id
                                    )
                                )
                            }
                        if (supportFragmentManager.backStackEntryCount == 0) {
                            showFilmsList()
                        }
                    }
                }
            })
    }

    private fun setBottomNavigationClickListener() {
        bottomNavigationView.setOnItemSelectedListener { item ->
            supportFragmentManager.popBackStack()
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
            .addToBackStack(FilmsListFragment.TAG)
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
            .addToBackStack(FavouriteFilmsListFragment.TAG)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            super.onBackPressed()
        } else {
            CustomDialog().show(supportFragmentManager, TAG_DIALOG)
        }
    }

    companion object {
        const val TAG_DIALOG = "dialog"
    }
}