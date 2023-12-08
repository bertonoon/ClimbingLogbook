package com.bf.climbinglogbook.ui.addNewAscent

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bf.climbinglogbook.MainCoroutineRule
import com.bf.climbinglogbook.getOrAwaitValue
import com.bf.climbinglogbook.models.AddAscentErrors
import com.bf.climbinglogbook.models.AscentStyle
import com.bf.climbinglogbook.models.BelayType
import com.bf.climbinglogbook.models.ClimbingType
import com.bf.climbinglogbook.models.GradeSystem
import com.bf.climbinglogbook.repositories.FakeMainRepository
import com.bf.climbinglogbook.repositories.GradesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class AddNewAscentViewModelTest {

    private lateinit var viewModel: AddNewAscentViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Before
    fun setup() {
        viewModel = AddNewAscentViewModel(GradesRepository(), FakeMainRepository())
    }

    @Test
    fun `set correct grade ordinal, expect true and 5 ordinal`() {
        assertTrue(viewModel.setGradeOrdinal(5))
        val value = viewModel.selectedGradeOrdinal.getOrAwaitValue()
        assertEquals(5, value)
    }

    @Test
    fun `set less than zero grade ordinal, expect false and 0 ordinal`() {
        assertFalse(viewModel.setGradeOrdinal(-1))
        val value = viewModel.selectedGradeOrdinal.getOrAwaitValue()
        assertEquals(0, value)
    }

    @Test
    fun `set more than max grade ordinal, expect false and 0 ordinal`() {
        assertFalse(viewModel.setGradeOrdinal(100))
        val value = viewModel.selectedGradeOrdinal.getOrAwaitValue()
        assertEquals(0, value)
    }

    @Test
    fun `set again default grade system, expect false and Kurtyka grade system`() {
        assertFalse(viewModel.setBaseGradeSystem(GradeSystem.KURTYKA))
        val value = viewModel.selectedBaseGradeSystem.getOrAwaitValue()
        assertEquals(GradeSystem.KURTYKA, value)
    }

    @Test
    fun `set grade system another than default, expect true and UIAA grade system`() {
        assertTrue(viewModel.setBaseGradeSystem(GradeSystem.UIAA))
        val value = viewModel.selectedBaseGradeSystem.getOrAwaitValue()
        assertEquals(GradeSystem.UIAA, value)
    }

    @Test
    fun `set correct base grade system and second time the same, expect true, next false and USA grade system`() {
        assertTrue(viewModel.setBaseGradeSystem(GradeSystem.USA))
        assertFalse(viewModel.setBaseGradeSystem(GradeSystem.USA))
        val value = viewModel.selectedBaseGradeSystem.getOrAwaitValue()
        assertEquals(GradeSystem.USA, value)
    }

    @Test
    fun `set correct date, expect true and change date`() {
        val date = Date()
        assertTrue(viewModel.setDate(date))
        val value = viewModel.date.getOrAwaitValue()
        assertEquals(date, value)
    }

    @Test
    fun `set date from future, expect failmsg and change date`() {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val date = sdf.parse("2321-12-12")!!
        assertTrue(viewModel.setDate(date))
        assertEquals(AddAscentErrors.DATE_FROM_FUTURE, viewModel.failMsg.getOrAwaitValue())
        assertEquals(date, viewModel.date.getOrAwaitValue())
    }

    @Test
    fun `set correct route name, expect true and name change`() {
        assertTrue(viewModel.setRouteName("validate name"))
        val value = viewModel.routeName.getOrAwaitValue()
        assertEquals("validate name", value)
    }

    @Test
    fun `set empty route name, expect false and empty routeName`() {
        assertFalse(viewModel.setRouteName(""))
        val value = viewModel.routeName.getOrAwaitValue()
        assertEquals("", value)
    }

    @Test
    fun `set to long route name, expect false and empty routeName`() {
        assertFalse(viewModel.setRouteName(toLongName()))
        val value = viewModel.routeName.getOrAwaitValue()
        assertEquals("", value)
    }

    @Test
    fun `set correct country, expect true and country change`() {
        assertTrue(viewModel.setCountry("validate name"))
        val value = viewModel.country.getOrAwaitValue()
        assertEquals("validate name", value)
    }

    @Test
    fun `set empty country, expect false and empty country`() {
        assertFalse(viewModel.setCountry(""))
        val value = viewModel.country.getOrAwaitValue()
        assertEquals("", value)
    }

    @Test
    fun `set to long country name, expect false and empty country`() {
        assertFalse(viewModel.setCountry(toLongName()))
        val value = viewModel.country.getOrAwaitValue()
        assertEquals("", value)
    }

    @Test
    fun `set correct region, expect true and region change`() {
        assertTrue(viewModel.setRegion("validate name"))
        val value = viewModel.region.getOrAwaitValue()
        assertEquals("validate name", value)
    }

    @Test
    fun `set empty region, expect false and empty region`() {
        assertFalse(viewModel.setRegion(""))
        val value = viewModel.region.getOrAwaitValue()
        assertEquals("", value)
    }

    @Test
    fun `set too long region name, expect false and empty region`() {
        assertFalse(viewModel.setRegion(toLongName()))
        val value = viewModel.region.getOrAwaitValue()
        assertEquals("", value)
    }

    @Test
    fun `set correct rock name, expect true and rock name change`() {
        assertTrue(viewModel.setRockName("validate name"))
        val value = viewModel.rockName.getOrAwaitValue()
        assertEquals("validate name", value)
    }

    @Test
    fun `set empty rock name, expect false and empty rock name`() {
        assertFalse(viewModel.setRockName(""))
        val value = viewModel.rockName.getOrAwaitValue()
        assertEquals("", value)
    }

    @Test
    fun `set too long rock name, expect false and empty rock name`() {
        assertFalse(viewModel.setRockName(toLongName()))
        val value = viewModel.rockName.getOrAwaitValue()
        assertEquals("", value)
    }

    @Test
    fun `set correct belayer name, expect true and belayer name change`() {
        assertTrue(viewModel.setBelayer("validate name"))
        val value = viewModel.belayer.getOrAwaitValue()
        assertEquals("validate name", value)
    }

    @Test
    fun `set empty belayer name, expect false and empty belayer name`() {
        assertFalse(viewModel.setBelayer(""))
        val value = viewModel.belayer.getOrAwaitValue()
        assertEquals("", value)
    }

    @Test
    fun `set too long belayer name, expect false and empty belayer name`() {
        assertFalse(viewModel.setBelayer(toLongName()))
        val value = viewModel.belayer.getOrAwaitValue()
        assertEquals("", value)
    }

    @Test
    fun `set ascent style, expect true and flash ascent style`() {
        assertTrue(viewModel.setAscentStyle(AscentStyle.FLASH))
        assertEquals(AscentStyle.FLASH, viewModel.selectedAscentStyle.getOrAwaitValue())
    }

    @Test
    fun `set the same ascent style twice, expect true,false and no change`() {
        assertTrue(viewModel.setAscentStyle(AscentStyle.FLASH))
        assertFalse(viewModel.setAscentStyle(AscentStyle.FLASH))
        assertEquals(AscentStyle.FLASH, viewModel.selectedAscentStyle.getOrAwaitValue())
    }

    @Test
    fun `set correct route length, expect true and 500m length`() {
        assertTrue(viewModel.setMeters(500))
        assertEquals(500, viewModel.meters.getOrAwaitValue())
    }

    @Test
    fun `set less than 0 route length, expect false`() {
        assertFalse(viewModel.setMeters(-1))
    }

    @Test
    fun `set more than 99999 route length, expect false`() {
        assertFalse(viewModel.setMeters(100_000))
    }

    @Test
    fun `set ClimbingType Sport, expect true and change to it`() {
        assertTrue(viewModel.setClimbingType(ClimbingType.SPORT))
        assertEquals(ClimbingType.SPORT, viewModel.selectedClimbingType.getOrAwaitValue())
    }

    @Test
    fun `set the same climbing type twice, expect true, then false and change no change`() {
        assertTrue(viewModel.setClimbingType(ClimbingType.SPORT))
        assertFalse(viewModel.setClimbingType(ClimbingType.SPORT))
        assertEquals(ClimbingType.SPORT, viewModel.selectedClimbingType.getOrAwaitValue())
    }

    @Test
    fun `set BelayType Lead, expect true and change to it`() {
        assertTrue(viewModel.setBelayType(BelayType.LEAD))
        assertEquals(BelayType.LEAD, viewModel.selectedBelayType.getOrAwaitValue())
    }

    @Test
    fun `set the same BelayType Lead twice, expect true, false and no change`() {
        assertTrue(viewModel.setBelayType(BelayType.LEAD))
        assertFalse(viewModel.setBelayType(BelayType.LEAD))
        assertEquals(BelayType.LEAD, viewModel.selectedBelayType.getOrAwaitValue())
    }

    @Test
    fun `set hard grade toggle true, expect true`() {
        assertTrue(viewModel.setHardGradeToggle(true))
        assertTrue(viewModel.hardGradeToggle.getOrAwaitValue())
    }

    @Test
    fun `set hard grade toggle true twice, expect true and next false and value=true`() {
        assertTrue(viewModel.setHardGradeToggle(true))
        assertFalse(viewModel.setHardGradeToggle(true))
        assertTrue(viewModel.hardGradeToggle.getOrAwaitValue())
    }

    @Test
    fun `check hard grade toggle default, expect false`() {
        assertFalse(viewModel.hardGradeToggle.getOrAwaitValue())
    }

    @Test
    fun `save correct ascent, expect true`() {
        assertTrue(viewModel.setRouteName("Validate name"))
        assertTrue(viewModel.setDate(Date()))
        assertTrue(viewModel.setBaseGradeSystem(GradeSystem.UIAA))
        assertTrue(viewModel.setGradeOrdinal(5))
        assertTrue(viewModel.setClimbingType(ClimbingType.SPORT))
        assertTrue(viewModel.setBelayType(BelayType.LEAD))
        assertTrue(viewModel.setAscentStyle(AscentStyle.FLASH))
        assertEquals(AddAscentErrors.NONE, viewModel.failMsg.getOrAwaitValue())
        assertTrue(viewModel.save())
        assertTrue(viewModel.successAdd.getOrAwaitValue())
    }

    @Test
    fun `save ascent with empty name, expect false and failMsg`() {
        assertFalse(viewModel.setRouteName(""))
        assertFalse(viewModel.save())
        assertEquals(AddAscentErrors.NULL_OR_EMPTY_NAME, viewModel.failMsg.getOrAwaitValue())
    }

    private fun toLongName(): String {
        var str = "a"
        while (str.length < 51) str += "a"
        return str
    }

    @Test
    fun `save ascent with to long name, expect false and failMsg`() {
        assertFalse(viewModel.setRouteName(toLongName()))
        assertFalse(viewModel.save())
        assertEquals(AddAscentErrors.NULL_OR_EMPTY_NAME, viewModel.failMsg.getOrAwaitValue())
    }

    @Test
    fun `save ascent with date from future, expect false and failMsg`() {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val futureDate = sdf.parse("2151-12-12")
        assertTrue(viewModel.setRouteName("validate name"))
        assertTrue(viewModel.setAscentStyle(AscentStyle.FLASH))
        assertTrue(viewModel.setDate(futureDate!!))
        assertEquals(AddAscentErrors.DATE_FROM_FUTURE, viewModel.failMsg.getOrAwaitValue())
        assertFalse(viewModel.save())
        assertEquals(AddAscentErrors.DATE_FROM_FUTURE, viewModel.failMsg.getOrAwaitValue())
    }

    @Test
    fun `save ascent without ascent style, expect false and failMsg`() {
        assertTrue(viewModel.setRouteName("validate name"))
        assertTrue(viewModel.setDate(Date()))
        assertFalse(viewModel.save())
        assertEquals(AddAscentErrors.NULL_ASCENT_STYLE, viewModel.failMsg.getOrAwaitValue())
    }


}