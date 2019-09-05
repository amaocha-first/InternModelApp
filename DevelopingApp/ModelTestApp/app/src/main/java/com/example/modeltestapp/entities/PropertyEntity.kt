package com.example.modeltestapp.entities

data class PropertyEntity(
    val price: String,
    val addressEntity: AddressEntity,
    val nearestStation: String,
    val distance: String
)