package com.acsoft.saveplacesxml.feature_places.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Place(
    val title : String,
    val description: String,
    val latitude : Double = 0.0,
    val longitude : Double = 0.0,
    @PrimaryKey
    val id : Int? = null
)
