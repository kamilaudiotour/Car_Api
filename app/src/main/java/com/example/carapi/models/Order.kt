package com.example.carapi.models

data class Order(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val foilVariant: String = "",
    val remarks: String = "",
    val car: Car = Car()
)
