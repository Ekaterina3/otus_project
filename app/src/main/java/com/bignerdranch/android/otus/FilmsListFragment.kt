package com.bignerdranch.android.otus

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * A fragment representing a list of Items.
 */
class FilmsListFragment : Fragment() {

    private val films by lazy {
        arguments?.getParcelableArrayList<FilmData>(ARG_FILMS)
    }
    private var recyclerView: RecyclerView? = null
    private var lastSelectedFilmIndex: Int = 0
    private var filmWasSelected: Boolean = false
    private var favouritesIdList: ArrayList<Int> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_films_list, container, false)
        if (view is RecyclerView) {
            with(view) {
                adapter = films?.let { film ->
                    FilmsAdapter(film) { item, position, type ->
                        onBtnClicked(type, item, position)
                    }
                }
            }
            recyclerView = view
        }

        return view
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
        recyclerView?.adapter?.notifyItemChanged(position)
    }

    private fun onDetailsBtnClicked(item: FilmData, position: Int) {
        resetSelection()
        selectFilm(item, position)

        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentContainer, DetailsFragment.newInstance(item), DetailsFragment.TAG)
            ?.addToBackStack(null)?.commit()
    }

    private fun resetSelection() {
        films?.get(lastSelectedFilmIndex)?.isSelected = false
        recyclerView?.adapter?.notifyItemChanged(lastSelectedFilmIndex)
    }

    private fun selectFilm(item: FilmData, position: Int) {
        item.isSelected = true
        recyclerView?.adapter?.notifyItemChanged(position)
        lastSelectedFilmIndex = position

        if (!filmWasSelected) {
            filmWasSelected = true
        }
    }

    companion object {
        const val TAG = "FilmsListFragment"
        const val KEY_FAVOURITES_BTN = "favourites_btn"
        const val KEY_DETAILS_BTN = "details_btn"
        const val ARG_FILMS = "films"

        @JvmStatic
        fun newInstance(films: ArrayList<FilmData>) =
            FilmsListFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_FILMS, films)
                }
            }
    }
}