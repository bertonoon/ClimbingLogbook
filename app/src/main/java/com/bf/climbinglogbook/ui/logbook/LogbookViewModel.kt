package com.bf.climbinglogbook.ui.logbook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bf.climbinglogbook.db.Ascent
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


    private val ascentsSortedByDateAsc = repository.getAllAscentsSortedByDateAsc()
    private val ascentsSortedByNameAsc = repository.getAllAscentsSortedByNameAsc()
    private val ascentsSortedByGradeAsc = repository.getAllAscentsSortedByGradeAsc()
    private val ascentsSortedByStyleAsc = repository.getAllAscentsSortedByStyleAsc()
    private val ascentsSortedByMetersAsc = repository.getAllAscentsSortedByMetersAsc()

    private val ascentsSortedByDateDesc = repository.getAllAscentsSortedByDateDesc()
    private val ascentsSortedByNameDesc = repository.getAllAscentsSortedByNameDesc()
    private val ascentsSortedByGradeDesc = repository.getAllAscentsSortedByGradeDesc()
    private val ascentsSortedByStyleDesc = repository.getAllAscentsSortedByStyleDesc()
    private val ascentsSortedByMetersDesc = repository.getAllAscentsSortedByMetersDesc()

    val ascents = MediatorLiveData<List<Ascent>>()

    private val _msg = MutableLiveData<LogbookMsg>().apply {
        value = LogbookMsg.NONE
    }
    val msg: LiveData<LogbookMsg> = _msg

    private val _sortType = MutableLiveData<SortType>().apply {
        value = SortType.DATE
    }
    val sortType: LiveData<SortType> = _sortType

    private val _sortDirectionDesc = MutableLiveData<Boolean>().apply {
        value = true
    }
    val sortDirectionDesc: LiveData<Boolean> = _sortDirectionDesc

    init {
        if (ascentsSortedByDateAsc != null)
            ascents.addSource(ascentsSortedByDateAsc) { result ->
                if (sortType.value == SortType.DATE && sortDirectionDesc.value == false) {
                    result?.let {
                        ascents.value = it
                    }
                }
            }
        if (ascentsSortedByNameAsc != null)
            ascents.addSource(ascentsSortedByNameAsc) { result ->
                if (sortType.value == SortType.NAME && sortDirectionDesc.value == false) {
                    result?.let {
                        ascents.value = it
                    }
                }
            }
        if (ascentsSortedByGradeAsc != null)
            ascents.addSource(ascentsSortedByGradeAsc) { result ->
                if (sortType.value == SortType.GRADE && sortDirectionDesc.value == false) {
                    result?.let {
                        ascents.value = it
                    }
                }
            }
        if (ascentsSortedByStyleAsc != null)
            ascents.addSource(ascentsSortedByStyleAsc) { result ->
                if (sortType.value == SortType.STYLE && sortDirectionDesc.value == false) {
                    result?.let {
                        ascents.value = it
                    }
                }
            }
        if (ascentsSortedByMetersAsc != null)
            ascents.addSource(ascentsSortedByMetersAsc) { result ->
                if (sortType.value == SortType.METERS && sortDirectionDesc.value == false) {
                    result?.let {
                        ascents.value = it
                    }
                }
            }
        if (ascentsSortedByDateDesc != null)
            ascents.addSource(ascentsSortedByDateDesc) { result ->
                if (sortType.value == SortType.DATE && sortDirectionDesc.value == true) {
                    result?.let {
                        ascents.value = it
                    }
                }
            }
        if (ascentsSortedByNameDesc != null)
            ascents.addSource(ascentsSortedByNameDesc) { result ->
                if (sortType.value == SortType.NAME && sortDirectionDesc.value == true) {
                    result?.let {
                        ascents.value = it
                    }
                }
            }
        if (ascentsSortedByGradeDesc != null)
            ascents.addSource(ascentsSortedByGradeDesc) { result ->
                if (sortType.value == SortType.GRADE && sortDirectionDesc.value == true) {
                    result?.let {
                        ascents.value = it
                    }
                }
            }
        if (ascentsSortedByStyleDesc != null)
            ascents.addSource(ascentsSortedByStyleDesc) { result ->
                if (sortType.value == SortType.STYLE && sortDirectionDesc.value == true) {
                    result?.let {
                        ascents.value = it
                    }
                }
            }
        if (ascentsSortedByMetersDesc != null)
            ascents.addSource(ascentsSortedByMetersDesc) { result ->
                if (sortType.value == SortType.METERS && sortDirectionDesc.value == true) {
                    result?.let {
                        ascents.value = it
                    }
                }
            }

    }

    fun sortAscents(sortType: SortType) = when (sortType) {
        SortType.DATE -> if (sortDirectionDesc.value!!) {
            ascentsSortedByDateDesc?.value?.let { ascents.value = it }
        } else ascentsSortedByDateAsc?.value?.let { ascents.value = it }

        SortType.NAME -> if (sortDirectionDesc.value!!) {
            ascentsSortedByNameDesc?.value?.let { ascents.value = it }
        } else ascentsSortedByNameAsc?.value?.let { ascents.value = it }

        SortType.GRADE -> if (sortDirectionDesc.value!!) {
            ascentsSortedByGradeDesc?.value?.let { ascents.value = it }
        } else ascentsSortedByGradeAsc?.value?.let { ascents.value = it }

        SortType.METERS -> if (sortDirectionDesc.value!!) {
            ascentsSortedByMetersDesc?.value?.let { ascents.value = it }
        } else ascentsSortedByMetersAsc?.value?.let { ascents.value = it }

        SortType.STYLE -> if (sortDirectionDesc.value!!) {
            ascentsSortedByStyleDesc?.value?.let { ascents.value = it }
        } else ascentsSortedByStyleAsc?.value?.let { ascents.value = it }

    }.also {
        _sortType.value = sortType
    }

    fun deleteAscent(ascent: Ascent) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAscent(ascent)
            _msg.postValue(LogbookMsg.SUCCESSFULLY_DELETE_RECORD)
        }
    }

    fun zeroMsg() {
        _msg.value = LogbookMsg.NONE
    }

    fun changeSortDirection() {
        _sortDirectionDesc.value = _sortDirectionDesc.value?.not()
    }
}