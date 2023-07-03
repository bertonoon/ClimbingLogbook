package com.bf.climbinglogbook.ui.gradeCalc

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bf.climbinglogbook.models.GradeSystem
import com.bf.climbinglogbook.models.gradeEnums.FrenchGrade
import com.bf.climbinglogbook.models.gradeEnums.KurtykaGrade
import com.bf.climbinglogbook.models.gradeEnums.USAGrade
import com.bf.climbinglogbook.other.GradeConverters

class GradeCalcViewModel(
) : ViewModel() {

    private val frenchGradesList = FrenchGrade.getList()
    private val kurtykaGradesList = KurtykaGrade.getList()
    private val usaGradesList = USAGrade.getList()


    val selectedBaseGradeSystem = MutableLiveData<GradeSystem>().apply {
        value = GradeSystem.KURTYKA
    }

    private val _grades = MutableLiveData<List<String>>().apply {
        value = when (selectedBaseGradeSystem.value) {
            GradeSystem.FRENCH -> frenchGradesList
            GradeSystem.KURTYKA -> kurtykaGradesList
            GradeSystem.USA -> usaGradesList
            else -> frenchGradesList
        }
    }
    val grades: LiveData<List<String>> = _grades

    private val _gradesMap = MutableLiveData<Map<GradeSystem, String>>()
    val gradesMap: LiveData<Map<GradeSystem, String>> = _gradesMap

    fun setGrades() {
        _grades.value = when (selectedBaseGradeSystem.value) {
            GradeSystem.FRENCH -> frenchGradesList
            GradeSystem.KURTYKA -> kurtykaGradesList
            GradeSystem.USA -> usaGradesList
            else -> frenchGradesList
        }
    }

    fun convertGrade(numberPickerValue: Int) {
        when (selectedBaseGradeSystem.value) {
            GradeSystem.FRENCH -> calculateFromFrenchGrade(numberPickerValue)
            GradeSystem.KURTYKA -> calculateFromKurtykaGrade(numberPickerValue)
            GradeSystem.USA -> calculateFromUsaGrade(numberPickerValue)
            null -> {
                _gradesMap.value = mapOf()
            }
        }
        Log.i("GradeConv", _gradesMap.value?.entries!!.joinToString())
    }

    private fun calculateFromUsaGrade(numberPickerValue: Int) {
        val usaGrade = USAGrade.values()[numberPickerValue]
        _gradesMap.value = mapOf(
            GradeSystem.USA to usaGrade.toString(),
            GradeSystem.KURTYKA to GradeConverters().usaToKurtyka(usaGrade).toString(),
            GradeSystem.FRENCH to GradeConverters().usaToFrench(usaGrade).toString()
        )
    }

    private fun calculateFromKurtykaGrade(numberPickerValue: Int) {
        val kurtykaGrade = KurtykaGrade.values()[numberPickerValue]
        _gradesMap.value = mapOf(
            GradeSystem.USA to GradeConverters().kurtykaToUsa(kurtykaGrade).toString(),
            GradeSystem.KURTYKA to kurtykaGrade.toString(),
            GradeSystem.FRENCH to GradeConverters().kurtykaToFrench(kurtykaGrade).toString()
        )
    }

    private fun calculateFromFrenchGrade(numberPickerValue: Int) {
        val frenchGrade = FrenchGrade.values()[numberPickerValue]
        _gradesMap.value = mapOf(
            GradeSystem.USA to GradeConverters().frenchToUsa(frenchGrade).toString(),
            GradeSystem.KURTYKA to GradeConverters().frenchToKurtyka(frenchGrade).toString(),
            GradeSystem.FRENCH to frenchGrade.toString()
        )
    }


}