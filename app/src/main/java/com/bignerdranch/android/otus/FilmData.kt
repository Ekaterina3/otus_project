package com.bignerdranch.android.otus

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilmData(val name: Int, val description: Int, val image: Int, val nameId: Int): Parcelable