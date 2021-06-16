package com.bignerdranch.android.otus

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class DetailsFragment : Fragment() {
    private var film: FilmData? = null

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
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        film?.let {
            view.findViewById<TextView>(R.id.titleView).text = getString(it.title)
            view.findViewById<TextView>(R.id.descriptionView).text = getString(it.description)
            view.findViewById<ImageView>(R.id.imageView).setImageResource(it.image)

            view.findViewById<Button>(R.id.inviteButton).setOnClickListener { _ ->
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "Интересный фильм, советую посмотреть: ${getString(it.title)}.")
                }

                try {
                    startActivity(Intent.createChooser(intent, "Send message with..."))
                } catch (ex: ActivityNotFoundException) {
                    Toast.makeText(
                        requireContext(),
                        "There are no suitable clients installed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
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