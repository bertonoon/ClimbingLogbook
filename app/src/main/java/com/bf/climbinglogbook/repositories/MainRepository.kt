package com.bf.climbinglogbook.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.bf.climbinglogbook.db.Ascent
import com.bf.climbinglogbook.db.AscentDAO
import javax.inject.Inject

@Suppress("MemberVisibilityCanBePrivate")
class MainRepository @Inject constructor(
    val ascentDao: AscentDAO
) {


    fun numberOfItemsInDB(): LiveData<Int>? {
        return try {
            ascentDao.numberOfItemsInDB()
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    fun lastAscent(num: Int): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getLastAscents(num)
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    fun getAllAscents(): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getAllAscents()
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    fun getAllAscentsSortedByDateAsc(): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getAllRoutesSortedByDateAsc()
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    fun getAllAscentsSortedByNameAsc(): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getAllRoutesSortedByNameAsc()
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    fun getAllAscentsSortedByGradeAsc(): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getAllRoutesSortedByGradeAsc()
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    fun getAllAscentsSortedByStyleAsc(): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getAllRoutesSortedByAscentStyleAsc()
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    fun getAllAscentsSortedByMetersAsc(): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getAllRoutesSortedByMetersAsc()
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    fun getAllAscentsSortedByDateDesc(): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getAllRoutesSortedByDateDesc()
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    fun getAllAscentsSortedByNameDesc(): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getAllRoutesSortedByNameDesc()
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    fun getAllAscentsSortedByGradeDesc(): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getAllRoutesSortedByGradeDesc()
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    fun getAllAscentsSortedByStyleDesc(): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getAllRoutesSortedByAscentStyleDesc()
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    fun getAllAscentsSortedByMetersDesc(): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getAllRoutesSortedByMetersDesc()
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            null
        }
    }

    fun insertAscent(ascent: Ascent): Boolean {
        return try {
            ascentDao.insertAscent(ascent)
            true
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            false
        }
    }

    fun deleteAscent(ascent: Ascent): Boolean {
        return try {
            ascentDao.deleteAscent(ascent)
            true
        } catch (e: Exception) {
            Log.e("RoomDb", e.toString())
            false
        }
    }

}