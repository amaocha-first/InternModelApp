package com.example.modeltestapp.entities

data class ResultEntity(
    val price: String,
    val address: AddressEntity,
    val nearestStation: String,
    val distance: String
)