package com.example.carapi.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Car(
    val id: Int = 0,
    val make: String = "",
    val model: String = "",
    val type: String = "",
    val year: String = ""
) : Parcelable