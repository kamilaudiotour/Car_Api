package com.example.carapi.models

data class Measurement(
    val car: Car = Car(),
    val size: String = "",
    var by: String = ""
)
