package com.bf.climbinglogbook.ui.home

import androidx.lifecycle.ViewModel
import com.bf.climbinglogbook.models.AscentStyle
import com.bf.climbinglogbook.models.ClimbingType
import com.bf.climbinglogbook.repositories.MainRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepositoryInterface
) : ViewModel() {

    val numOsAscents = repository.numberOfOsAscentsByAscentStyle(AscentStyle.ON_SIGHT)
    val numRpAscents = repository.numberOfOsAscentsByAscentStyle(AscentStyle.REDPOINT)
    val numFlashAscents = repository.numberOfOsAscentsByAscentStyle(AscentStyle.FLASH)
    val numSportAscents = repository.numberOfOsAscentsByClimbingType(ClimbingType.SPORT)
    val numTradAscents = repository.numberOfOsAscentsByClimbingType(ClimbingType.TRAD)
    val numAllAscents = repository.numberOfItemsInDB()

}