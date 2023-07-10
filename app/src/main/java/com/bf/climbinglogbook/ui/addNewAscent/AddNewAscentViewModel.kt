package com.bf.climbinglogbook.ui.addNewAscent

import android.util.Log
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bf.climbinglogbook.R
import com.bf.climbinglogbook.db.Ascent
import com.bf.climbinglogbook.models.AddAscentErrors
import com.bf.climbinglogbook.models.GradeSystem
import com.bf.climbinglogbook.models.gradeEnums.FrenchGrade
import com.bf.climbinglogbook.models.gradeEnums.KurtykaGrade
import com.bf.climbinglogbook.models.gradeEnums.UIAAGrade
import com.bf.climbinglogbook.models.gradeEnums.USAGrade
import com.bf.climbinglogbook.repositories.GradesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AddNewAscentViewModel @Inject constructor(
    private val gradeRepo: GradesRepository
) : ViewModel() {

    private val grades = GradesRepository().getGradesMap()

    private val _hardGradeToggle = MutableLiveData<Boolean>().apply {
        value = false
    }
    val hardGradeToggle: LiveData<Boolean> = _hardGradeToggle

    private val _selectedBaseGradeSystem = MutableLiveData<GradeSystem>().apply {
        value = GradeSystem.KURTYKA
    }
    val selectedBaseGradeSystem: LiveData<GradeSystem> = _selectedBaseGradeSystem

    private val _selectedGradesList = MutableLiveData<List<String>>().apply {
        value = grades[selectedBaseGradeSystem.value ?: GradeSystem.KURTYKA]
    }
    val selectedGradesList: LiveData<List<String>> = _selectedGradesList

    private val _routeName = MutableLiveData<String>()
    val routeName: LiveData<String> = _routeName

    private val _failMsg = MutableLiveData<AddAscentErrors>()
    val failMsg: LiveData<AddAscentErrors> = _failMsg

    private val _date = MutableLiveData<Date>().apply {
        value = Date()
    }
    val date : LiveData<Date> = _date

    fun setBaseGradeSystem(newBaseGradeSystem: GradeSystem) {
        if (newBaseGradeSystem == _selectedBaseGradeSystem.value) return
        _selectedBaseGradeSystem.value = newBaseGradeSystem
        setGrades()
    }

    private fun validateRouteName(): Boolean {
        val name = routeName.value
        if (name.isNullOrEmpty()) {
            _failMsg.value = AddAscentErrors.NULL_OR_EMPTY_NAME
            return false
        }
        if (name.length > 50) {
            _failMsg.value = AddAscentErrors.TO_LONG_NAME
            return false
        }
        return true
    }

    fun save() {
        if (!validateRouteName()) return
        Log.i("AddNewAscent", "Success")
    }

    fun setRouteName(name: String) {
        if (name.isEmpty()) return
        _routeName.value = name
    }

    fun setHardGradeToggle(checked: Boolean) {
        if (checked == hardGradeToggle.value) return
        _hardGradeToggle.value = checked
    }

    private fun setGrades() {
        _selectedGradesList.value =
            grades[selectedBaseGradeSystem.value] ?: grades[GradeSystem.KURTYKA]
    }

}