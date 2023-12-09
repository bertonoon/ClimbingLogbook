package com.bf.climbinglogbook.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bf.climbinglogbook.MainCoroutineRule
import com.bf.climbinglogbook.db.Ascent
import com.bf.climbinglogbook.db.TestAscents
import com.bf.climbinglogbook.getOrAwaitValue
import com.bf.climbinglogbook.models.AscentStyle
import com.bf.climbinglogbook.models.ClimbingType
import com.bf.climbinglogbook.repositories.FakeMainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private lateinit var ascents: List<Ascent>

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        val fakeMainRepository = FakeMainRepository()
        ascents = TestAscents.getList()
        for (ascent in ascents) {
            fakeMainRepository.insertAscent(ascent)
        }
        viewModel = HomeViewModel(fakeMainRepository)

    }

    @Test
    fun getNumOsAscents() {
        val countOsAscents = ascents.count { it.ascentStyle == AscentStyle.ON_SIGHT }
        assertEquals(viewModel.numOsAscents?.getOrAwaitValue() ?: -1, countOsAscents)
    }

    @Test
    fun getNumRpAscents() {
        val countRpAscents = ascents.count { it.ascentStyle == AscentStyle.REDPOINT }
        assertEquals(viewModel.numRpAscents?.getOrAwaitValue() ?: -1, countRpAscents)
    }

    @Test
    fun getNumFlashAscents() {
        val countFlAscents = ascents.count { it.ascentStyle == AscentStyle.FLASH }
        assertEquals(viewModel.numFlashAscents?.getOrAwaitValue() ?: -1, countFlAscents)
    }

    @Test
    fun getNumSportAscents() {
        val countSportAscents = ascents.count { it.climbingType == ClimbingType.SPORT }
        assertEquals(viewModel.numSportAscents?.getOrAwaitValue() ?: -1, countSportAscents)
    }

    @Test
    fun getNumTradAscents() {
        val countTradAscents = ascents.count { it.climbingType == ClimbingType.TRAD }
        assertEquals(viewModel.numTradAscents?.getOrAwaitValue() ?: -1, countTradAscents)
    }

    @Test
    fun getNumAscents() {
        val countAllAscents = ascents.size
        assertEquals(viewModel.numAllAscents?.getOrAwaitValue() ?: -1, countAllAscents)
    }
}