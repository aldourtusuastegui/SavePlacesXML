package com.acsoft.saveplacesxml.feature_places.domain.repository

import com.acsoft.saveplacesxml.feature_places.domain.model.Place
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {

    fun getPlacesList(): Flow<List<Place>>

    suspend fun getPlaceById(id: Int): Place?

    suspend fun insertPlace(place: Place)

    suspend fun deletePlace(place: Place)
}