package com.bignerdranch.android.otus

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilmData(
        val title: String,
        val description: String?,
        val image: String,
        var wasVisited: Boolean = false,
        var isFavourite: Boolean = false,
        val id: Int
): Parcelable