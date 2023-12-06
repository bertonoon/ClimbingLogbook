package com.bf.climbinglogbook.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.bf.climbinglogbook.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class AscentDAOTest {

    private lateinit var database: LogbookDatabase
    private lateinit var dao: AscentDAO
    private lateinit var ascents: List<Ascent>

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LogbookDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.getAscentDao()
        ascents = TestAscents.getList()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAscent() = runTest {
        val ascent = ascents[0]
        dao.insertAscent(ascent)
        val ascents = dao.getAllAscents().getOrAwaitValue()
        assertTrue(ascents.contains(ascent))
    }

    @Test
    fun deleteAscent() = runTest {
        val ascent = ascents[0]
        dao.insertAscent(ascent)
        val ascentsFromDb = dao.getAllAscents().getOrAwaitValue()
        assertTrue(ascentsFromDb.contains(ascent))

        dao.deleteAscent(ascentsFromDb[0])
        val ascentsAfterDelete = dao.getAllAscents().getOrAwaitValue()
        assertFalse(ascentsAfterDelete.contains(ascent))
    }

    private fun insertAscents() {
        for (ascent in ascents) {
            dao.insertAscent(ascent)
        }
    }

    @Test
    fun getAllRoutesSortedByNameAsc() = runTest {
        insertAscents()
        val ascentsFromDb = dao.getAllRoutesSortedByNameAsc().getOrAwaitValue()

        val ascentsSortedByName = ascents.sortedBy { it.name }
        assertEquals(ascentsSortedByName, ascentsFromDb)
    }
}