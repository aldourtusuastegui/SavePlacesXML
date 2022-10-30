package com.acsoft.saveplacesxml.feature_places.presentation.add_place

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acsoft.saveplacesxml.feature_places.domain.model.Place
import com.acsoft.saveplacesxml.feature_places.domain.use_case.PlaceUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPlaceViewModel @Inject constructor(private val placeUseCases: PlaceUseCases,
private val savedStateHandle: SavedStateHandle) : ViewModel() {

    fun registerPlace(place: Place) {
        viewModelScope.launch {
            placeUseCases.insertPlaceUseCase.invoke(place)
        }
    }

    fun getTitleArg() : String {
        val title = savedStateHandle.get<String>("title")
        return if (title.isNullOrEmpty()) "" else title
    }

    fun getDescriptionArg() : String {
        val description = savedStateHandle.get<String>("description")
        return if (description.isNullOrEmpty()) "" else description
    }

}