package com.bignerdranch.android.otus

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar

class FavouriteFilmsListFragment : Fragment() {

    private val films by lazy {
        arguments?.getParcelableArrayList<FilmData>(ARG_FILMS)
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var mainToolbar: Toolbar
    private lateinit var emptyTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_films_list, container, false)
        initElements(view)
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
                    removeFromFavourites(film, position, view)
                }
            })
        }
    }

    private fun removeFromFavourites(item: FilmData, position: Int, view: View) {
        item.isFavourite = false
        films?.removeAt(position)
        recyclerView.adapter?.notifyItemRemoved(position)
        recyclerView.adapter?.notifyItemChanged(position)
        checkListOnEmpty()
        showSnakbar(view, item, position)
    }

    private fun showSnakbar(view: View, item: FilmData, position: Int) {
        val snackbar = Snackbar.make(
            view,
            getString(R.string.removed_from_favourites, getString(item.title)),
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction(getString(R.string.cancel)) {
            addToFavourites(item, position)
        }
        snackbar.show()
    }

    private fun addToFavourites(item: FilmData, position: Int) {
        item.isFavourite = true
        films?.add(position, item)
        recyclerView.adapter?.notifyItemInserted(position)
        recyclerView.adapter?.notifyItemChanged(position)
        checkListOnEmpty()
    }

    private fun checkListOnEmpty() {
        if (films?.isEmpty() == true) {
            emptyTextView.visibility = View.VISIBLE
        } else {
            emptyTextView.visibility = View.GONE
        }
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

    private fun initElements(view: View) {
        mainToolbar = (activity as AppCompatActivity).findViewById(R.id.toolbarMain)
        emptyTextView = view.findViewById(R.id.emptyTitle)
    }

    override fun onResume() {
        super.onResume()
        mainToolbar.setTitle(R.string.favourites)
        checkListOnEmpty()
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