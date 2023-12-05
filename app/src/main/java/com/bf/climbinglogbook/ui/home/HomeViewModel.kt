package com.bf.climbinglogbook.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bf.climbinglogbook.models.AscentStyle
import com.bf.climbinglogbook.models.ClimbingType
import com.bf.climbinglogbook.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    val allOsAscents = repository.numberOfOsAscentsByAscentStyle(AscentStyle.ON_SIGHT)
    val allRpAscents = repository.numberOfOsAscentsByAscentStyle(AscentStyle.REDPOINT)
    val allFlashAscents = repository.numberOfOsAscentsByAscentStyle(AscentStyle.FLASH)
    val allSportAscents = repository.numberOfOsAscentsByClimbingType(ClimbingType.SPORT)
    val allTradAscents = repository.numberOfOsAscentsByClimbingType(ClimbingType.TRAD)
    val allAscents = repository.numberOfItemsInDB()

}