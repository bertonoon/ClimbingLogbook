package com.bf.climbinglogbook.ui.logbook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bf.climbinglogbook.db.Ascent
import com.bf.climbinglogbook.db.AscentDAO
import com.bf.climbinglogbook.models.LogbookMsg
import com.bf.climbinglogbook.models.SortType
import com.bf.climbinglogbook.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogbookViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {


    private val ascentsSortedByDate = repository.getAllAscentsSortedByDateAsc()
    private val ascentsSortedByName = repository.getAllAscentsSortedByNameAsc()
    private val ascentsSortedByGrade = repository.getAllAscentsSortedByGradeAsc()
    private val ascentsSortedByStyle = repository.getAllAscentsSortedByStyleAsc()
    private val ascentsSortedByMeters = repository.getAllAscentsSortedByMetersAsc()

    val ascents = MediatorLiveData<List<Ascent>>()

    private val _msg = MutableLiveData<LogbookMsg>()
    val msg: LiveData<LogbookMsg> = _msg

    private val _sortType = MutableLiveData<SortType>().apply {
        value = SortType.DATE
    }
    val sortType: LiveData<SortType> = _sortType

    init {
        if (ascentsSortedByDate != null)
            ascents.addSource(ascentsSortedByDate) { result ->
                if (sortType.value == SortType.DATE) {
                    result?.let {
                        ascents.value = it
                    }
                }
            }
        if (ascentsSortedByName != null)
            ascents.addSource(ascentsSortedByName) { result ->
                if (sortType.value == SortType.NAME) {
                    result?.let {
                        ascents.value = it
                    }
                }
            }
        if (ascentsSortedByGrade != null)
            ascents.addSource(ascentsSortedByGrade) { result ->
                if (sortType.value == SortType.DATE) {
                    result?.let {
                        ascents.value = it
                    }
                }
            }
        if (ascentsSortedByStyle != null)
            ascents.addSource(ascentsSortedByStyle) { result ->
                if (sortType.value == SortType.STYLE) {
                    result?.let {
                        ascents.value = it
                    }
                }
            }
        if (ascentsSortedByMeters != null)
            ascents.addSource(ascentsSortedByMeters) { result ->
                if (sortType.value == SortType.DATE) {
                    result?.let {
                        ascents.value = it
                    }
                }
            }

    }

    fun sortAscents(sortType: SortType) = when (sortType) {
        SortType.DATE -> ascentsSortedByDate?.value?.let { ascents.value = it }
        SortType.NAME -> ascentsSortedByName?.value?.let { ascents.value = it }
        SortType.GRADE -> ascentsSortedByGrade?.value?.let { ascents.value = it }
        SortType.STYLE -> ascentsSortedByStyle?.value?.let { ascents.value = it }
        SortType.METERS -> ascentsSortedByMeters?.value?.let { ascents.value = it }
    }.also {
        _sortType.value = sortType
    }

    fun deleteAscent(ascent: Ascent) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAscent(ascent)
            _msg.postValue(LogbookMsg.SUCCESSFULLY_DELETE_RECORD)
        }
    }
}