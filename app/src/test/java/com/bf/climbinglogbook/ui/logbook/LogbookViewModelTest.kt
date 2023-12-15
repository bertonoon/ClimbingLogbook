package com.bf.climbinglogbook.ui.logbook

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bf.climbinglogbook.MainCoroutineRule
import com.bf.climbinglogbook.db.Ascent
import com.bf.climbinglogbook.db.TestAscents
import com.bf.climbinglogbook.getOrAwaitValue
import com.bf.climbinglogbook.models.LogbookMsg
import com.bf.climbinglogbook.models.SortType
import com.bf.climbinglogbook.repositories.FakeMainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LogbookViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()


    private lateinit var mockRepository: FakeMainRepository

    @Mock
    private lateinit var ascentsObserver: Observer<List<Ascent>>

    private lateinit var viewModel: LogbookViewModel
    private lateinit var ascents: List<Ascent>


    @Before
    fun setUp() {
        mockRepository = FakeMainRepository()
        ascents = TestAscents.getList()
        for (ascent in ascents) {
            mockRepository.insertAscent(ascent)
        }

        viewModel = LogbookViewModel(mockRepository)
        viewModel.ascents.observeForever {  }

    }

    @After
    fun tearDown() {
        viewModel.ascents.removeObserver(ascentsObserver)
    }

    @Test
    fun `zeroMsg, expects LogbookMsg NONE`() {
        viewModel.zeroMsg()
        assertEquals(LogbookMsg.NONE, viewModel.msg.getOrAwaitValue())
    }

    @Test
    fun `getAllAscents, expects ascents`() {
        //viewModel.sortAscents(SortType.DATE)
        assertEquals(ascents.sortedByDescending { it.date }, viewModel.ascents.getOrAwaitValue())
    }


}