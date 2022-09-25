package com.acsoft.saveplacesxml.feature_places.data.data_source

import androidx.room.*
import com.acsoft.saveplacesxml.feature_places.domain.model.Place
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {

    @Query("SELECT * FROM place")
    fun getPlacesList(): Flow<List<Place>>

    @Query("SELECT * FROM place WHERE id = :id")
    suspend fun getPlaceById(id: Int): Place?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlace(place: Place)

    @Delete
    suspend fun deletePlace(place: Place)
}