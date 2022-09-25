package com.acsoft.saveplacesxml.di

import android.app.Application
import androidx.room.Room
import com.acsoft.saveplacesxml.feature_places.data.data_source.PlaceDatabase
import com.acsoft.saveplacesxml.feature_places.data.repository.PlaceRepositoryImpl
import com.acsoft.saveplacesxml.feature_places.domain.repository.PlaceRepository
import com.acsoft.saveplacesxml.feature_places.domain.use_case.GetPlacesListUseCase
import com.acsoft.saveplacesxml.feature_places.domain.use_case.InsertPlaceUseCase
import com.acsoft.saveplacesxml.feature_places.domain.use_case.PlaceUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePlaceDatabase(app: Application) : PlaceDatabase {
        return Room.databaseBuilder(
            app,
            PlaceDatabase::class.java,
            PlaceDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providePlaceRepository(db: PlaceDatabase) : PlaceRepository {
        return PlaceRepositoryImpl(db.placeDao)
    }

    @Provides
    @Singleton
    fun providePlaceUseCases(repository: PlaceRepository) : PlaceUseCases {
        return PlaceUseCases(
            getPlacesListUseCase = GetPlacesListUseCase(repository),
            insertPlaceUseCase = InsertPlaceUseCase(repository),
        )
    }

}