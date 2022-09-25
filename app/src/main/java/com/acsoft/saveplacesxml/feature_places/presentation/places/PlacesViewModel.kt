package com.acsoft.saveplacesxml.feature_places.presentation.places

import androidx.lifecycle.ViewModel
import com.acsoft.saveplacesxml.feature_places.domain.model.Place
import com.acsoft.saveplacesxml.feature_places.domain.use_case.PlaceUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val placeUseCases: PlaceUseCases
) : ViewModel() {

    fun getPlacesList() : Flow<List<Place>> {
        return placeUseCases.getPlacesListUseCase.invoke()
    }
}