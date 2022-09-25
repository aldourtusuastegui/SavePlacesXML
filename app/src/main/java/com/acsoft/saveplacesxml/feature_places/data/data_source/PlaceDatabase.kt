package com.acsoft.saveplacesxml.feature_places.data.data_source

import androidx.room.Database
import com.acsoft.saveplacesxml.feature_places.domain.model.Place

@Database(entities = [Place::class], version = 1, exportSchema = false)
abstract class PlaceDatabase {

    abstract val placeDao: PlaceDao

    companion object {
        const val DATABASE_NAME = "place_db"
    }
}