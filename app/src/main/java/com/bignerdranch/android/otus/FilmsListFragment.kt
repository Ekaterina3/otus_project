package com.bignerdranch.android.otus

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar

class FilmsListFragment : Fragment() {

    private val films by lazy {
        arguments?.getParcelableArrayList<FilmData>(ARG_FILMS)
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var mainToolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_films_list, container, false)
        initToolbar()
        initRecycler(view)
        return view
    }

    private fun initRecycler(view: View) {
        recyclerView = view.findViewById(R.id.filmsRecycler)
        recyclerView.adapter = films?.let { film ->
            FilmsAdapter(film, object :
                FilmsAdapter.ItemClickListener {
                override fun detailsBtnClickListener(film: FilmData, position: Int) {
                    onDetailsBtnClicked(film, position)
                }

                override fun favouritesBtnClickListener(film: FilmData, position: Int) {
                    onFavouritesBtnClicked(film, position, view)
                }
            })
        }
    }

    private fun onFavouritesBtnClicked(item: FilmData, position: Int, view: View) {
        item.isFavourite = !item.isFavourite
        recyclerView.adapter?.notifyItemChanged(position)
        showSnackbar(item, view)
    }

    private fun showSnackbar(item: FilmData, view: View) {
        val name = item.title
        val msg = if (item.isFavourite) getString(
            R.string.added_to_favourites,
            name
        ) else getString(R.string.removed_from_favourites, name)
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
    }

    private fun onDetailsBtnClicked(item: FilmData, position: Int) {
        selectFilm(item, position)

        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(
                R.id.fragmentContainer,
                DetailsFragment.newInstance(item),
                DetailsFragment.TAG
            )
            ?.addToBackStack(null)?.commit()
    }

    private fun selectFilm(item: FilmData, position: Int) {
        item.wasVisited = true
        recyclerView.adapter?.notifyItemChanged(position)
    }

    private fun initToolbar() {
        mainToolbar = (activity as AppCompatActivity).findViewById(R.id.toolbarMain)
    }

    override fun onResume() {
        super.onResume()
        mainToolbar.setTitle(R.string.home)
    }

    companion object {
        const val TAG = "FilmsListFragment"
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