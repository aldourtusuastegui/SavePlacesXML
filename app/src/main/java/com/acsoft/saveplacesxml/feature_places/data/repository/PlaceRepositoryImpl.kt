package com.acsoft.saveplacesxml.feature_places.data.repository

import com.acsoft.saveplacesxml.feature_places.data.data_source.PlaceDao
import com.acsoft.saveplacesxml.feature_places.domain.model.Place
import com.acsoft.saveplacesxml.feature_places.domain.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow

class PlaceRepositoryImpl(private val dao: PlaceDao) : PlaceRepository {
    override fun getPlacesList(): Flow<List<Place>> {
        return dao.getPlacesList()
    }

    override suspend fun getPlaceById(id: Int): Place? {
        return dao.getPlaceById(id)
    }

    override suspend fun insertPlace(place: Place) {
        return dao.insertPlace(place)
    }

    override suspend fun deletePlace(place: Place) {
        return dao.deletePlace(place)
    }
}