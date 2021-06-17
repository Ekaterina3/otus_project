package com.bignerdranch.android.otus

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class DetailsFragment : Fragment() {
    private var film: FilmData? = null
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            film = it.getParcelable(ARG_FILM_DATA)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)

        hideMainToolbar()
        initToolbar(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        film?.let {
            toolbar.title = getString(it.title)
            view.findViewById<TextView>(R.id.descriptionView).text = getString(it.description)
            view.findViewById<ImageView>(R.id.imageView).setImageResource(it.image)

//            view.findViewById<Button>(R.id.inviteButton).setOnClickListener { _ ->
//                val intent = Intent(Intent.ACTION_SEND).apply {
//                    type = "text/plain"
//                    putExtra(Intent.EXTRA_TEXT, "Интересный фильм, советую посмотреть: ${getString(it.title)}.")
//                }
//
//                try {
//                    startActivity(Intent.createChooser(intent, "Send message with..."))
//                } catch (ex: ActivityNotFoundException) {
//                    Toast.makeText(
//                        requireContext(),
//                        "There are no suitable clients installed.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
        }
    }

    private fun initToolbar(view: View) {
        toolbar = view.findViewById<View>(R.id.toolbarDetails) as Toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            (activity as AppCompatActivity).onBackPressed()
        }
    }

    private fun hideMainToolbar() {
        (activity as AppCompatActivity).findViewById<View>(R.id.toolbarMain).visibility =
            View.GONE
    }

    private fun showMainToolbar() {
        (activity as AppCompatActivity).findViewById<View>(R.id.toolbarMain).visibility =
            View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        showMainToolbar()
    }

    companion object {
        const val TAG = "FilmsListFragment"
        private const val ARG_FILM_DATA = "FILM_DATA"

        @JvmStatic
        fun newInstance(film: FilmData) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                  putParcelable(ARG_FILM_DATA, film)
                }
            }
    }
}