package com.acsoft.saveplacesxml.feature_places.presentation.places

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.acsoft.saveplacesxml.feature_places.domain.model.Place
import com.acsoft.saveplacesxml.feature_places.domain.use_case.PlaceUseCases
import com.acsoft.saveplacesxml.workers.FileWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val placeUseCases: PlaceUseCases,
    application: Application
) : ViewModel() {

    private val workManager = WorkManager.getInstance(application)

    internal fun getPlacesList() : Flow<List<Place>> {
        return placeUseCases.getPlacesListUseCase.invoke()
    }

    internal fun workManagerExample(placeList: List<Place>) {
        val data = Data.Builder()
        data.putString("data", placeList.toString())
        val fileWorker = OneTimeWorkRequest
            .Builder(FileWorker::class.java)
            .setInputData(data.build())
            .build()
        workManager.enqueue((fileWorker))
    }
}