package com.acsoft.saveplacesxml.feature_places.domain.use_case

import com.acsoft.saveplacesxml.feature_places.domain.model.Place
import com.acsoft.saveplacesxml.feature_places.domain.repository.PlaceRepository

class InsertPlaceUseCase(private val repository: PlaceRepository) {

    suspend operator fun invoke(place: Place) {
        repository.insertPlace(place)
    }
}