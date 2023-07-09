package com.bf.climbinglogbook.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.bf.climbinglogbook.db.LogbookDatabase
import com.bf.climbinglogbook.other.Constants
import com.bf.climbinglogbook.repositories.GradesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLogbookDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        LogbookDatabase::class.java,
        Constants.LOGBOOK_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideAscentDao(db : LogbookDatabase) = db.getAscentDao()

    @Singleton
    @Provides
    fun provideGradesRepository() = GradesRepository()

}