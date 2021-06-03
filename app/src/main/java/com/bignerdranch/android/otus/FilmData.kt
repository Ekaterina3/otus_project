package com.bignerdranch.android.otus

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilmData(
        val title: Int,
        val description: Int,
        val image: Int,
        var isSelected: Boolean = false,
        var isFavourite: Boolean = false,
        val id: Int
): Parcelable