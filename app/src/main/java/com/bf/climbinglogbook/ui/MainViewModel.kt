package com.bf.climbinglogbook.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bf.climbinglogbook.db.Ascent
import com.bf.climbinglogbook.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    val numberOfAscents = repository.numberOfItemsInDB()
    val lastThreeAscents = repository.lastAscent(3)
    val allAscents = repository.getAllAscents()

    private val _ascentToDisplay = MutableLiveData<Ascent>()
    val ascentToDisplay: LiveData<Ascent> = _ascentToDisplay

    fun setAscentToDisplay(ascent: Ascent): Boolean {
        _ascentToDisplay.postValue(ascent)
        return true
    }


}