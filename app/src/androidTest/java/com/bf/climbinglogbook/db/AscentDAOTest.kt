package com.bf.climbinglogbook.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.bf.climbinglogbook.getOrAwaitValue
import com.bf.climbinglogbook.models.AscentStyle
import com.bf.climbinglogbook.models.ClimbingType
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class AscentDAOTest {

    @Inject
    @Named("test_db")
    lateinit var database: LogbookDatabase
    private lateinit var dao: AscentDAO
    private lateinit var ascents: List<Ascent>

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        hiltRule.inject()
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

        val ascentsSorted = ascents.sortedBy { it.name }
        assertEquals(ascentsSorted, ascentsFromDb)
    }

    @Test
    fun getAllRoutesSortedByNameDesc() = runTest {
        insertAscents()
        val ascentsFromDb = dao.getAllRoutesSortedByNameDesc().getOrAwaitValue()

        val ascentsSorted = ascents.sortedByDescending { it.name }
        assertEquals(ascentsSorted, ascentsFromDb)
    }

    @Test
    fun getAllRoutesSortedByDateAsc() = runTest {
        insertAscents()
        val ascentsFromDb = dao.getAllRoutesSortedByDateAsc().getOrAwaitValue()

        val ascentsSorted = ascents.sortedBy { it.date }
        assertEquals(ascentsSorted, ascentsFromDb)
    }

    @Test
    fun getAllRoutesSortedByDateDesc() = runTest {
        insertAscents()
        val ascentsFromDb = dao.getAllRoutesSortedByDateDesc().getOrAwaitValue()

        val ascentsSorted = ascents.sortedByDescending { it.date }
        assertEquals(ascentsSorted, ascentsFromDb)
    }

    @Test
    fun getAllRoutesSortedByAscentStyleAsc() = runTest {
        insertAscents()
        val ascentsFromDb = dao.getAllRoutesSortedByAscentStyleAsc().getOrAwaitValue()

        val ascentsSorted = ascents.sortedBy { it.ascentStyle.name }
        assertEquals(ascentsSorted, ascentsFromDb)
    }

    @Test
    fun getAllRoutesSortedByAscentStyleDesc() = runTest {
        insertAscents()
        val ascentsFromDb = dao.getAllRoutesSortedByAscentStyleDesc().getOrAwaitValue()

        val ascentsSorted = ascents.sortedByDescending { it.ascentStyle.name }
        assertEquals(ascentsSorted, ascentsFromDb)
    }

    @Test
    fun getAllRoutesSortedByMetersAsc() = runTest {
        insertAscents()
        val ascentsFromDb = dao.getAllRoutesSortedByMetersAsc().getOrAwaitValue()

        val ascentsSorted = ascents.sortedBy { it.meters }
        assertEquals(ascentsSorted, ascentsFromDb)
    }

    @Test
    fun getAllRoutesSortedByMetersDesc() = runTest {
        insertAscents()
        val ascentsFromDb = dao.getAllRoutesSortedByMetersDesc().getOrAwaitValue()

        val ascentsSorted = ascents.sortedByDescending { it.meters }
        assertEquals(ascentsSorted, ascentsFromDb)
    }

    @Test
    fun getAllRoutesSortedByGradeAsc() = runTest {
        insertAscents()
        val ascentsFromDb = dao.getAllRoutesSortedByGradeAsc().getOrAwaitValue()

        val ascentsSorted = ascents.sortedBy { it.usaGradeNumber }
        assertEquals(ascentsSorted, ascentsFromDb)
    }

    @Test
    fun getAllRoutesSortedByGradeDesc() = runTest {
        insertAscents()
        val ascentsFromDb = dao.getAllRoutesSortedByGradeDesc().getOrAwaitValue()

        val ascentsSorted = ascents.sortedByDescending { it.usaGradeNumber }
        assertEquals(ascentsSorted, ascentsFromDb)
    }

    @Test
    fun numberOfItemsInDb() = runTest {
        insertAscents()
        val numberOfItemsInDb = dao.numberOfItemsInDB().getOrAwaitValue()
        assertEquals(numberOfItemsInDb, ascents.size)
    }

    @Test
    fun numberOfAscentsByStyle() = runTest {
        insertAscents()
        val numberOfItemsInDb = dao.numberOfAscentsByStyle(AscentStyle.REDPOINT).getOrAwaitValue()
        val numOfRedpointAscents = ascents.count { it.ascentStyle == AscentStyle.REDPOINT }
        assertEquals(numberOfItemsInDb, numOfRedpointAscents)
    }

    @Test
    fun numberOfAscentsByClimbingType() = runTest {
        insertAscents()
        val numberOfItemsInDb =
            dao.numberOfAscentsByClimbingType(ClimbingType.SPORT).getOrAwaitValue()
        val numOfSportAscents = ascents.count { it.climbingType == ClimbingType.SPORT }
        assertEquals(numberOfItemsInDb, numOfSportAscents)
    }

    @Test
    fun getLastAscents_lastThree() = runTest {
        insertAscents()
        val ascentsInDb = dao.getLastAscents(3).getOrAwaitValue()
        val lastThreeAscents = ascents.sortedByDescending { it.date }.take(3)
        assertEquals(ascentsInDb, lastThreeAscents)
    }

    @Test
    fun getLastAscents_empty_expectEmptyList() = runTest {
        insertAscents()
        val ascentsInDb = dao.getLastAscents(0).getOrAwaitValue()
        assertEquals(ascentsInDb, listOf<Ascent>())
    }

    @Test
    fun getLastAscents_minusOne_expectAllAscents() = runTest {
        insertAscents()
        val ascentsInDb = dao.getLastAscents(-1).getOrAwaitValue()
        assertEquals(ascentsInDb, ascents.sortedByDescending { it.date })
    }

    @Test
    fun getLastAscents_overSize_expectAllAscents() = runTest {
        insertAscents()
        val ascentsInDb = dao.getLastAscents(31).getOrAwaitValue()
        assertEquals(ascentsInDb, ascents.sortedByDescending { it.date })
    }


}