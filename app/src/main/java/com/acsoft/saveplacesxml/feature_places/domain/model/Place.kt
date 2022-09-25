package com.acsoft.saveplacesxml.feature_places.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Place(
    val title : String,
    val description: String,
    val latitude : String,
    val longitude : String,
    val timestamp: Long,
    @PrimaryKey
    val id : Int? = null
)
