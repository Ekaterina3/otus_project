package com.bignerdranch.android.otus

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class FilmsListFragment : Fragment() {

    private val films by lazy {
        arguments?.getParcelableArrayList<FilmData>(ARG_FILMS)
    }
    private var recyclerView: RecyclerView? = null
    private var favouritesIdList: ArrayList<Int> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_films_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.filmsRecycler)
        recyclerView?.apply {
            this.adapter = films?.let { films ->
                FilmsAdapter(films) { item, position, type ->
                    onItemClicked(type, item, position)
                }
            }
        }
    }

    private fun onItemClicked(type: String, item: FilmData, position: Int) {
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
        selectFilm(item, position)
    }

    private fun selectFilm(item: FilmData, position: Int) {
        item.wasVisited = true
        recyclerView?.adapter?.notifyItemChanged(position)
        (activity as? OnItemClickListener)?.onDetailsBtnClicked(item)
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

    interface OnItemClickListener {
        fun onDetailsBtnClicked(item: FilmData)
    }
}