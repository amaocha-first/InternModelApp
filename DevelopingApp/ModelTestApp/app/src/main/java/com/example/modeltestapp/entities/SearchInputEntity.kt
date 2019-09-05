package com.example.modeltestapp.entities

data class SearchInputEntity(
    val minPrice: Int,
    val maxPrice: Int,
    val areaText: String,
    val keyword: String
)