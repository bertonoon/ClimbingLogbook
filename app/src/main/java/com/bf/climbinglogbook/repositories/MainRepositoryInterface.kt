package com.bf.climbinglogbook.repositories

import androidx.lifecycle.LiveData
import com.bf.climbinglogbook.db.Ascent
import com.bf.climbinglogbook.models.AscentStyle
import com.bf.climbinglogbook.models.ClimbingType

interface MainRepositoryInterface {

    fun numberOfItemsInDB(): LiveData<Int>?

    fun lastAscent(num: Int): LiveData<List<Ascent>>?

    fun getAllAscents(): LiveData<List<Ascent>>?

    fun getAllAscentsSortedByDateAsc(): LiveData<List<Ascent>>?

    fun getAllAscentsSortedByNameAsc(): LiveData<List<Ascent>>?

    fun getAllAscentsSortedByGradeAsc(): LiveData<List<Ascent>>?

    fun getAllAscentsSortedByStyleAsc(): LiveData<List<Ascent>>?

    fun getAllAscentsSortedByMetersAsc(): LiveData<List<Ascent>>?

    fun getAllAscentsSortedByDateDesc(): LiveData<List<Ascent>>?

    fun getAllAscentsSortedByNameDesc(): LiveData<List<Ascent>>?

    fun getAllAscentsSortedByGradeDesc(): LiveData<List<Ascent>>?

    fun getAllAscentsSortedByStyleDesc(): LiveData<List<Ascent>>?

    fun getAllAscentsSortedByMetersDesc(): LiveData<List<Ascent>>?

    fun numberOfOsAscentsByAscentStyle(ascentStyle: AscentStyle): LiveData<Int>?

    fun numberOfOsAscentsByClimbingType(climbingType: ClimbingType): LiveData<Int>?

    fun insertAscent(ascent: Ascent): Boolean

    fun deleteAscent(ascent: Ascent): Boolean
}