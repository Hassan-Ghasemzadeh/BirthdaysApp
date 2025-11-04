package com.lifeandme.domain.model

data class Birthday(
    val id: Long = 0,
    val name: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int = 0,
    val minute: Int = 0
)
