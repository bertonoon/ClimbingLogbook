package com.bf.climbinglogbook.ui.gradeCalc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bf.climbinglogbook.models.GradeSystem
import com.bf.climbinglogbook.models.gradeEnums.FrenchGrade
import com.bf.climbinglogbook.models.gradeEnums.KurtykaGrade
import com.bf.climbinglogbook.models.gradeEnums.UIAAGrade
import com.bf.climbinglogbook.models.gradeEnums.USAGrade
import com.bf.climbinglogbook.other.GradeConverters
import com.bf.climbinglogbook.repositories.GradesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GradeCalcViewModel @Inject constructor(
    private val gradeRepo: GradesRepository
) : ViewModel() {

    private val gradesLists = GradesRepository().getGradesMap()

    private val frenchGradesList = gradesLists[GradeSystem.FRENCH]
    private val kurtykaGradesList = gradesLists[GradeSystem.KURTYKA]
    private val usaGradesList = gradesLists[GradeSystem.USA]
    private val uiaaGradesList = gradesLists[GradeSystem.UIAA]

    private val _hardGradeToggle = MutableLiveData<Boolean>().apply {
        value = false
    }
    val hardGradeToggle: LiveData<Boolean> = _hardGradeToggle


    private val _selectedBaseGradeSystem = MutableLiveData<GradeSystem>().apply {
        value = GradeSystem.KURTYKA
    }
    val selectedBaseGradeSystem: LiveData<GradeSystem> = _selectedBaseGradeSystem

    private val _grades = MutableLiveData<List<String>>().apply {
        value = when (selectedBaseGradeSystem.value) {
            GradeSystem.FRENCH -> frenchGradesList
            GradeSystem.KURTYKA -> kurtykaGradesList
            GradeSystem.USA -> usaGradesList
            GradeSystem.UIAA -> uiaaGradesList
            else -> frenchGradesList
        }
    }
    val grades: LiveData<List<String>> = _grades

    private val _gradesCalcResult = MutableLiveData<Map<GradeSystem, String>>()
    val gradesMap: LiveData<Map<GradeSystem, String>> = _gradesCalcResult

    private fun setGrades() {
        _grades.value = when (selectedBaseGradeSystem.value) {
            GradeSystem.FRENCH -> frenchGradesList
            GradeSystem.KURTYKA -> kurtykaGradesList
            GradeSystem.USA -> usaGradesList
            GradeSystem.UIAA -> uiaaGradesList
            else -> frenchGradesList
        }
    }

    fun convertGrade(numberPickerValue: Int) {
        val hard = hardGradeToggle.value ?: false
        when (selectedBaseGradeSystem.value) {
            GradeSystem.FRENCH -> calculateFromFrenchGrade(numberPickerValue, hard)
            GradeSystem.KURTYKA -> calculateFromKurtykaGrade(numberPickerValue, hard)
            GradeSystem.USA -> calculateFromUsaGrade(numberPickerValue, hard)
            GradeSystem.UIAA -> calculateFromUiaaGrade(numberPickerValue, hard)
            null -> {
                _gradesCalcResult.value = mapOf()
            }
        }
    }

    private fun calculateFromUsaGrade(numberPickerValue: Int, hard: Boolean) {
        val usaGrade = USAGrade.values()[numberPickerValue]
        _gradesCalcResult.value = mapOf(
            GradeSystem.USA to usaGrade.toString(),
            GradeSystem.KURTYKA to GradeConverters().usaToKurtyka(usaGrade, hard).toString(),
            GradeSystem.FRENCH to GradeConverters().usaToFrench(usaGrade, hard).toString(),
            GradeSystem.UIAA to GradeConverters().usaToUiaa(usaGrade, hard).toString(),
        )
    }

    private fun calculateFromKurtykaGrade(numberPickerValue: Int, hard: Boolean) {
        val kurtykaGrade = KurtykaGrade.values()[numberPickerValue]
        _gradesCalcResult.value = mapOf(
            GradeSystem.USA to GradeConverters().kurtykaToUsa(kurtykaGrade, hard).toString(),
            GradeSystem.KURTYKA to kurtykaGrade.toString(),
            GradeSystem.FRENCH to GradeConverters().kurtykaToFrench(kurtykaGrade, hard).toString(),
            GradeSystem.UIAA to GradeConverters().kurtykaToUiaa(kurtykaGrade, hard).toString(),
        )
    }

    private fun calculateFromFrenchGrade(numberPickerValue: Int, hard: Boolean) {
        val frenchGrade = FrenchGrade.values()[numberPickerValue]
        _gradesCalcResult.value = mapOf(
            GradeSystem.USA to GradeConverters().frenchToUsa(frenchGrade, hard).toString(),
            GradeSystem.KURTYKA to GradeConverters().frenchToKurtyka(frenchGrade, hard).toString(),
            GradeSystem.FRENCH to frenchGrade.toString(),
            GradeSystem.UIAA to GradeConverters().frenchToUiaa(frenchGrade, hard).toString()
        )
    }

    private fun calculateFromUiaaGrade(numberPickerValue: Int, hard: Boolean) {
        val uiaaGrade = UIAAGrade.values()[numberPickerValue]
        _gradesCalcResult.value = mapOf(
            GradeSystem.USA to GradeConverters().uiaaToUsa(uiaaGrade, hard).toString(),
            GradeSystem.KURTYKA to GradeConverters().uiaaToKurtyka(uiaaGrade, hard).toString(),
            GradeSystem.FRENCH to GradeConverters().uiaaToFrench(uiaaGrade, hard).toString(),
            GradeSystem.UIAA to uiaaGrade.toString()
        )
    }

    fun setBaseGradeSystem(newBaseGradeSystem: GradeSystem) {
        if (newBaseGradeSystem == _selectedBaseGradeSystem.value) return
        _selectedBaseGradeSystem.value = newBaseGradeSystem
        setGrades()
    }

    fun setHardGradeToggle(checked: Boolean) {
        if (checked == _hardGradeToggle.value) return
        _hardGradeToggle.value = checked
    }


}