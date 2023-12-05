package com.bf.climbinglogbook.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bf.climbinglogbook.models.AscentStyle
import com.bf.climbinglogbook.models.ClimbingType
import com.bf.climbinglogbook.other.Constants.ROUTES_TABLE_NAME

@Dao
interface AscentDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAscent(ascent: Ascent)

    @Delete
    fun deleteAscent(ascent: Ascent)

    @Query("SELECT * FROM $ROUTES_TABLE_NAME ORDER BY name ASC")
    fun getAllRoutesSortedByNameAsc(): LiveData<List<Ascent>>

    @Query("SELECT * FROM $ROUTES_TABLE_NAME ORDER BY date ASC")
    fun getAllRoutesSortedByDateAsc(): LiveData<List<Ascent>>

    @Query("SELECT * FROM $ROUTES_TABLE_NAME ORDER BY usaGradeNumber ASC")
    fun getAllRoutesSortedByGradeAsc(): LiveData<List<Ascent>>

    @Query("SELECT * FROM $ROUTES_TABLE_NAME ORDER BY ascentStyle ASC")
    fun getAllRoutesSortedByAscentStyleAsc(): LiveData<List<Ascent>>

    @Query("SELECT * FROM $ROUTES_TABLE_NAME ORDER BY meters ASC")
    fun getAllRoutesSortedByMetersAsc(): LiveData<List<Ascent>>

    @Query("SELECT * FROM $ROUTES_TABLE_NAME ORDER BY name DESC")
    fun getAllRoutesSortedByNameDesc(): LiveData<List<Ascent>>

    @Query("SELECT * FROM $ROUTES_TABLE_NAME ORDER BY date DESC")
    fun getAllRoutesSortedByDateDesc(): LiveData<List<Ascent>>

    @Query("SELECT * FROM $ROUTES_TABLE_NAME ORDER BY usaGradeNumber DESC")
    fun getAllRoutesSortedByGradeDesc(): LiveData<List<Ascent>>

    @Query("SELECT * FROM $ROUTES_TABLE_NAME ORDER BY ascentStyle DESC")
    fun getAllRoutesSortedByAscentStyleDesc(): LiveData<List<Ascent>>

    @Query("SELECT * FROM $ROUTES_TABLE_NAME ORDER BY meters DESC")
    fun getAllRoutesSortedByMetersDesc(): LiveData<List<Ascent>>

    @Query("SELECT * FROM $ROUTES_TABLE_NAME ORDER BY date DESC LIMIT :num")
    fun getLastAscents(num: Int): LiveData<List<Ascent>>

    @Query("SELECT count(id) FROM $ROUTES_TABLE_NAME")
    fun numberOfItemsInDB(): LiveData<Int>

    @Query("SELECT count(id) FROM $ROUTES_TABLE_NAME WHERE ascentStyle LIKE :ascentStyle")
    fun numberOfOsAscentsByStyle(ascentStyle: AscentStyle): LiveData<Int>

    @Query("SELECT count(id) FROM $ROUTES_TABLE_NAME WHERE ascentStyle LIKE :climbingType")
    fun numberOfOsAscentsByClimbingType(climbingType: ClimbingType): LiveData<Int>

    @Query("SELECT * FROM $ROUTES_TABLE_NAME")
    fun getAllAscents(): LiveData<List<Ascent>>
}