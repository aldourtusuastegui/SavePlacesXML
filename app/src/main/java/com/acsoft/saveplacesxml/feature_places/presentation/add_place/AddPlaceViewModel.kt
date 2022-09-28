package com.acsoft.saveplacesxml.feature_places.presentation.add_place

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acsoft.saveplacesxml.feature_places.domain.model.Place
import com.acsoft.saveplacesxml.feature_places.domain.use_case.PlaceUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPlaceViewModel @Inject constructor(private val placeUseCases: PlaceUseCases) : ViewModel() {

    fun registerPlace(place: Place) {
        viewModelScope.launch {
            placeUseCases.insertPlaceUseCase.invoke(place)
        }
    }
}