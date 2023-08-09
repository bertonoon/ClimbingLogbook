package com.bf.climbinglogbook.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bf.climbinglogbook.db.Ascent
import com.bf.climbinglogbook.db.AscentDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception
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
        } catch (e: Exception){
            Log.e("RoomDb", e.toString())
            null
        }
    }

    fun getAllAscents(): LiveData<List<Ascent>>? {
        return try {
            ascentDao.getAllAscents()
        } catch (e: Exception){
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
}