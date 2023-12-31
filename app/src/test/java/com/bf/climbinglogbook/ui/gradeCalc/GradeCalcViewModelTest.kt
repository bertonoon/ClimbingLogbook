package com.bf.climbinglogbook.ui.gradeCalc

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bf.climbinglogbook.models.GradeSystem
import com.bf.climbinglogbook.models.gradeEnums.FrenchGrade
import com.bf.climbinglogbook.models.gradeEnums.KurtykaGrade
import com.bf.climbinglogbook.models.gradeEnums.UIAAGrade
import com.bf.climbinglogbook.models.gradeEnums.USAGrade
import com.bf.climbinglogbook.repositories.GradesRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class GradeCalcViewModelTest {

    private lateinit var viewModel: GradeCalcViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setup() {
        viewModel = GradeCalcViewModel(GradesRepository())

    }

    @Test
    fun setBaseSystemToKurtyka() {
        viewModel.setBaseGradeSystem(GradeSystem.KURTYKA)
        assertEquals(GradeSystem.KURTYKA, viewModel.selectedBaseGradeSystem.value)
    }

    @Test
    fun setBaseSystemToFrench() {
        viewModel.setBaseGradeSystem(GradeSystem.FRENCH)
        assertEquals(GradeSystem.FRENCH, viewModel.selectedBaseGradeSystem.value)
    }

    @Test
    fun setBaseSystemToUsa() {
        viewModel.setBaseGradeSystem(GradeSystem.USA)
        assertEquals(GradeSystem.USA, viewModel.selectedBaseGradeSystem.value)
    }

    @Test
    fun setBaseSystemToUiaa() {
        viewModel.setBaseGradeSystem(GradeSystem.UIAA)
        assertEquals(GradeSystem.UIAA, viewModel.selectedBaseGradeSystem.value)
    }

    @Test(expected = ArrayIndexOutOfBoundsException::class)
    fun setBaseSystemToOverRange() {
        viewModel.setBaseGradeSystem(GradeSystem.values()[111])
    }

    @Test(expected = ArrayIndexOutOfBoundsException::class)
    fun setBaseSystemToUnderRange() {
        viewModel.setBaseGradeSystem(GradeSystem.values()[-1])
    }

    @Test
    fun setFrenchGradeSystem_returnGradesLiveDataWithFrenchList() {
        viewModel.setBaseGradeSystem(GradeSystem.FRENCH)
        assertEquals(FrenchGrade.getList(), viewModel.grades.value)
    }

    @Test
    fun setKurtykaGradeSystem_returnGradesLiveDataWithKurtykaList() {
        viewModel.setBaseGradeSystem(GradeSystem.KURTYKA)
        assertEquals(KurtykaGrade.getList(), viewModel.grades.value)
    }

    @Test
    fun setUsaGradeSystem_returnGradesLiveDataWithUsaList() {
        viewModel.setBaseGradeSystem(GradeSystem.USA)
        assertEquals(USAGrade.getList(), viewModel.grades.value)
    }

    @Test
    fun setUiaaGradeSystem_returnGradesLiveDataWithUiaaList() {
        viewModel.setBaseGradeSystem(GradeSystem.UIAA)
        assertEquals(UIAAGrade.getList(), viewModel.grades.value)
    }

    @Test
    fun setHardGradeToggleTest_setToTrueFromToggle() {
        viewModel.setHardGradeToggle(true)
        assertEquals(true, viewModel.hardGradeToggle.value)
    }

    @Test
    fun setHardGradeToggleTest_setToFalseFromToggle() {
        viewModel.setHardGradeToggle(false)
        assertEquals(false, viewModel.hardGradeToggle.value)
    }

    @Test
    fun convertGradeTest_fromFrenchMiddleValue() {
        val numberPickerValue = 16
        viewModel.setBaseGradeSystem(GradeSystem.FRENCH)
        viewModel.convertGrade(numberPickerValue)

        val expectedResult = mapOf(
            Pair(GradeSystem.FRENCH, "7a"),
            Pair(GradeSystem.KURTYKA, "VI.3"),
            Pair(GradeSystem.USA, "5.11c"),
            Pair(GradeSystem.UIAA, "VIII")
        )

        assertEquals(expectedResult, viewModel.gradesMap.value)
    }

    @Test
    fun convertGradeTest_fromFrenchMaxValue() {
        val numberPickerValue = 32
        viewModel.setBaseGradeSystem(GradeSystem.FRENCH)
        viewModel.convertGrade(numberPickerValue)

        val expectedResult = mapOf(
            Pair(GradeSystem.FRENCH, "9c"),
            Pair(GradeSystem.KURTYKA, "VI.10"),
            Pair(GradeSystem.USA, "5.15d"),
            Pair(GradeSystem.UIAA, "XII+")
        )

        assertEquals(expectedResult, viewModel.gradesMap.value)
    }

    @Test
    fun convertGradeTest_fromFrenchMinValue() {
        val numberPickerValue = 0
        viewModel.setBaseGradeSystem(GradeSystem.FRENCH)
        viewModel.convertGrade(numberPickerValue)

        val expectedResult = mapOf(
            Pair(GradeSystem.FRENCH, "1"),
            Pair(GradeSystem.KURTYKA, "I"),
            Pair(GradeSystem.USA, "5.1"),
            Pair(GradeSystem.UIAA, "I")
        )

        assertEquals(expectedResult, viewModel.gradesMap.value)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun convertGradeTest_overRangeValue() {
        val numberPickerValue = 51
        viewModel.convertGrade(numberPickerValue)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun convertGradeTest_underRangeValue() {
        val numberPickerValue = -1
        viewModel.convertGrade(numberPickerValue)
    }

    @Test
    fun convertGradeTest_fromFrenchWithParamHardTrue() {
        val numberPickerValue = 9
        viewModel.setBaseGradeSystem(GradeSystem.FRENCH)
        viewModel.setHardGradeToggle(true)
        viewModel.convertGrade(numberPickerValue)

        val expectedResult = mapOf(
            Pair(GradeSystem.FRENCH, "5c"),
            Pair(GradeSystem.KURTYKA, "VI-"),
            Pair(GradeSystem.USA, "5.8"),
            Pair(GradeSystem.UIAA, "VI-")
        )
        assertEquals(expectedResult, viewModel.gradesMap.value)
    }

    @Test
    fun convertGradeTest_fromUsa() {
        val numberPickerValue = 8
        viewModel.setBaseGradeSystem(GradeSystem.USA)
        viewModel.convertGrade(numberPickerValue)

        val expectedResult = mapOf(
            Pair(GradeSystem.FRENCH, "5c"),
            Pair(GradeSystem.KURTYKA, "VI-"),
            Pair(GradeSystem.USA, "5.8"),
            Pair(GradeSystem.UIAA, "VI-")
        )
        assertEquals(expectedResult, viewModel.gradesMap.value)
    }

    @Test
    fun convertGradeTest_fromKurtykaWithHardParamTrue() {
        val numberPickerValue = 19
        viewModel.setBaseGradeSystem(GradeSystem.KURTYKA)
        viewModel.setHardGradeToggle(true)
        viewModel.convertGrade(numberPickerValue)

        val expectedResult = mapOf(
            Pair(GradeSystem.FRENCH, "7c"),
            Pair(GradeSystem.KURTYKA, "VI.4+"),
            Pair(GradeSystem.USA, "5.12d"),
            Pair(GradeSystem.UIAA, "IX")
        )
        assertEquals(expectedResult, viewModel.gradesMap.value)
    }

    @Test
    fun convertGradeTest_fromKurtykaWithHardParamFalse() {
        val numberPickerValue = 19
        viewModel.setBaseGradeSystem(GradeSystem.KURTYKA)
        viewModel.setHardGradeToggle(false)
        viewModel.convertGrade(numberPickerValue)

        val expectedResult = mapOf(
            Pair(GradeSystem.FRENCH, "7b+"),
            Pair(GradeSystem.KURTYKA, "VI.4+"),
            Pair(GradeSystem.USA, "5.12c"),
            Pair(GradeSystem.UIAA, "IX")
        )
        assertEquals(expectedResult, viewModel.gradesMap.value)
    }


    @Test
    fun convertGradeTest_fromUiaaWithHardParamTrue() {
        val numberPickerValue = 25
        viewModel.setBaseGradeSystem(GradeSystem.UIAA)
        viewModel.setHardGradeToggle(true)
        viewModel.convertGrade(numberPickerValue)

        val expectedResult = mapOf(
            Pair(GradeSystem.FRENCH, "8c+"),
            Pair(GradeSystem.KURTYKA, "VI.7+"),
            Pair(GradeSystem.USA, "5.14c"),
            Pair(GradeSystem.UIAA, "XI-")
        )
        assertEquals(expectedResult, viewModel.gradesMap.value)
    }

    @Test
    fun convertGradeTest_fromUiaaWithHardParamFalse() {
        val numberPickerValue = 25
        viewModel.setBaseGradeSystem(GradeSystem.UIAA)
        viewModel.setHardGradeToggle(false)
        viewModel.convertGrade(numberPickerValue)

        val expectedResult = mapOf(
            Pair(GradeSystem.FRENCH, "8c"),
            Pair(GradeSystem.KURTYKA, "VI.7"),
            Pair(GradeSystem.USA, "5.14b"),
            Pair(GradeSystem.UIAA, "XI-")
        )
        assertEquals(expectedResult, viewModel.gradesMap.value)
    }


}