package com.example.modeltestapp.entities

data class AddressEntity(
    val prefecture: String,
    val city: String,
    val tyome: String,
    val address: String = prefecture + city + tyome
)