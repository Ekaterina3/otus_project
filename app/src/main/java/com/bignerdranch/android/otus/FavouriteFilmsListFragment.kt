package com.bignerdranch.android.otus

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class FavouriteFilmsListFragment : Fragment() {

    private val films by lazy {
        arguments?.getParcelableArrayList<FilmData>(ARG_FILMS)
    }
    private var recyclerView: RecyclerView? = null
    private var favouritesIdList: ArrayList<Int> = ArrayList()
    private lateinit var mainToolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_films_list, container, false)
        initToolbar()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.filmsRecycler)
        recyclerView?.apply {
            this.adapter = films?.let { film ->
                FilmsAdapter(film, object :
                    FilmsAdapter.ItemClickListener {
                    override fun detailsBtnClickListener(film: FilmData, position: Int) {
                        onDetailsBtnClicked(film, position)
                    }
                    override fun favouritesBtnClickListener(film: FilmData, position: Int) {
                        onFavouritesBtnClicked(film, position)
                    }
                }
                )
            }
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
//        resetSelection()
        selectFilm(item, position)

        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentContainer, DetailsFragment.newInstance(item), DetailsFragment.TAG)
            ?.addToBackStack(null)?.commit()
    }

//    private fun resetSelection() {
//        films?.get(lastSelectedFilmIndex)?.isSelected = false
//        recyclerView?.adapter?.notifyItemChanged(lastSelectedFilmIndex)
//    }

    private fun selectFilm(item: FilmData, position: Int) {
        item.wasVisited = true
        recyclerView?.adapter?.notifyItemChanged(position)
    }

    private fun initToolbar() {
        mainToolbar = (activity as AppCompatActivity).findViewById(R.id.toolbarMain)
    }

    override fun onResume() {
        super.onResume()
        mainToolbar.setTitle(R.string.favourites)
    }

    companion object {
        const val TAG = "FavouriteFilmsListFragment"
        const val ARG_FILMS = "films"

        @JvmStatic
        fun newInstance(films: ArrayList<FilmData>) =
            FavouriteFilmsListFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_FILMS, films)
                }
            }
    }
}