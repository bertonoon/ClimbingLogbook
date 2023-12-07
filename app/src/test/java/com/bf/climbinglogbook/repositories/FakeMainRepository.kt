package com.bf.climbinglogbook.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bf.climbinglogbook.db.Ascent
import com.bf.climbinglogbook.models.AscentStyle
import com.bf.climbinglogbook.models.ClimbingType

class FakeMainRepository : MainRepositoryInterface{

    private val ascents = mutableListOf<Ascent>()

    private val observableAscents = MutableLiveData<List<Ascent>>(ascents)
    private val observableNumberOfAscents = MutableLiveData<Int>()

    private fun refreshLiveData(){
        observableAscents.postValue(ascents)
        observableNumberOfAscents.postValue(ascents.size)
    }

    override fun numberOfItemsInDB(): LiveData<Int>? {
        return observableNumberOfAscents
    }

    override fun lastAscent(num: Int): LiveData<List<Ascent>>? {
        val lastAscents = ascents.sortedByDescending { it.date }.take(num)
        return MutableLiveData(lastAscents)
    }

    override fun getAllAscents(): LiveData<List<Ascent>>? {
        return observableAscents
    }

    override fun getAllAscentsSortedByDateAsc(): LiveData<List<Ascent>>? {
        return observableAscents.apply {
            value?.sortedBy { it.date }
        }
    }

    override fun getAllAscentsSortedByNameAsc(): LiveData<List<Ascent>>? {
        return observableAscents.apply {
            value?.sortedBy { it.name }
        }
    }

    override fun getAllAscentsSortedByGradeAsc(): LiveData<List<Ascent>>? {
        return observableAscents.apply {
            value?.sortedBy { it.usaGradeNumber }
        }
    }

    override fun getAllAscentsSortedByStyleAsc(): LiveData<List<Ascent>>? {
        return observableAscents.apply {
            value?.sortedBy { it.ascentStyle }
        }
    }

    override fun getAllAscentsSortedByMetersAsc(): LiveData<List<Ascent>>? {
        return observableAscents.apply {
            value?.sortedBy { it.meters }
        }
    }

    override fun getAllAscentsSortedByDateDesc(): LiveData<List<Ascent>>? {
        return observableAscents.apply {
            value?.sortedByDescending { it.date }
        }
    }

    override fun getAllAscentsSortedByNameDesc(): LiveData<List<Ascent>>? {
        return observableAscents.apply {
            value?.sortedByDescending { it.name }
        }
    }

    override fun getAllAscentsSortedByGradeDesc(): LiveData<List<Ascent>>? {
        return observableAscents.apply {
            value?.sortedByDescending { it.usaGradeNumber }
        }
    }

    override fun getAllAscentsSortedByStyleDesc(): LiveData<List<Ascent>>? {
        return observableAscents.apply {
            value?.sortedByDescending { it.ascentStyle }
        }
    }

    override fun getAllAscentsSortedByMetersDesc(): LiveData<List<Ascent>>? {
        return observableAscents.apply {
            value?.sortedByDescending { it.meters }
        }
    }

    override fun numberOfOsAscentsByAscentStyle(ascentStyle: AscentStyle): LiveData<Int>? {
        val numOfAscents = ascents.count { it.ascentStyle == ascentStyle }
        return MutableLiveData(numOfAscents)
    }

    override fun numberOfOsAscentsByClimbingType(climbingType: ClimbingType): LiveData<Int>? {
        val numOfAscents = ascents.count { it.climbingType == climbingType }
        return MutableLiveData(numOfAscents)
    }

    override fun insertAscent(ascent: Ascent): Boolean {
        ascents.add(ascent)
        refreshLiveData()
        return ascents.contains(ascent)
    }

    override fun deleteAscent(ascent: Ascent): Boolean {
        ascents.remove(ascent)
        refreshLiveData()
        return ascents.contains(ascent)
    }
}