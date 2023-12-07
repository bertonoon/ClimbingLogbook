package com.bf.climbinglogbook.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.bf.climbinglogbook.db.Ascent
import com.bf.climbinglogbook.db.AscentDAO
import com.bf.climbinglogbook.models.AscentStyle
import com.bf.climbinglogbook.models.ClimbingType
import javax.inject.Inject

@Suppress("MemberVisibilityCanBePrivate")
class MainRepository @Inject constructor(
    val ascentDao: AscentDAO
) : MainRepositoryInterface {


    override fun numberOfItemsInDB(): LiveData<Int>? {
        return try {
            ascentDao.numberOfItemsInDB()
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    override fun lastAscent(num: Int): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getLastAscents(num)
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    override fun getAllAscents(): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getAllAscents()
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    override fun getAllAscentsSortedByDateAsc(): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getAllRoutesSortedByDateAsc()
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    override fun getAllAscentsSortedByNameAsc(): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getAllRoutesSortedByNameAsc()
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    override fun getAllAscentsSortedByGradeAsc(): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getAllRoutesSortedByGradeAsc()
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    override fun getAllAscentsSortedByStyleAsc(): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getAllRoutesSortedByAscentStyleAsc()
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    override fun getAllAscentsSortedByMetersAsc(): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getAllRoutesSortedByMetersAsc()
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    override fun getAllAscentsSortedByDateDesc(): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getAllRoutesSortedByDateDesc()
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    override fun getAllAscentsSortedByNameDesc(): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getAllRoutesSortedByNameDesc()
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    override fun getAllAscentsSortedByGradeDesc(): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getAllRoutesSortedByGradeDesc()
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    override fun getAllAscentsSortedByStyleDesc(): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getAllRoutesSortedByAscentStyleDesc()
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    override fun getAllAscentsSortedByMetersDesc(): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getAllRoutesSortedByMetersDesc()
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    override fun numberOfOsAscentsByAscentStyle(ascentStyle: AscentStyle): LiveData<Int>? {
        return try {
            ascentDao.numberOfAscentsByStyle(ascentStyle)
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    override fun numberOfOsAscentsByClimbingType(climbingType: ClimbingType): LiveData<Int>? {
        return try {
            ascentDao.numberOfAscentsByClimbingType(climbingType)
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    override fun insertAscent(ascent: Ascent): Boolean {
        return try {
            ascentDao.insertAscent(ascent)
            true
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            false
        }
    }

    override fun deleteAscent(ascent: Ascent): Boolean {
        return try {
            ascentDao.deleteAscent(ascent)
            true
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            false
        }
    }

}