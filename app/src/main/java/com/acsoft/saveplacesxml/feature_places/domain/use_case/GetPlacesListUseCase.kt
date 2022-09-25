package com.acsoft.saveplacesxml.feature_places.domain.use_case

import com.acsoft.saveplacesxml.feature_places.domain.model.Place
import com.acsoft.saveplacesxml.feature_places.domain.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow

class GetPlacesListUseCase(private val repository: PlaceRepository) {

    operator fun invoke(): Flow<List<Place>> {
        return repository.getPlacesList()
    }
}