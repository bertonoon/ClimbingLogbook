package com.bf.climbinglogbook.ui.addNewAscent

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bf.climbinglogbook.models.AddAscentErrors
import com.bf.climbinglogbook.models.AscentStyle
import com.bf.climbinglogbook.models.BelayType
import com.bf.climbinglogbook.models.ClimbingType
import com.bf.climbinglogbook.models.GradeSystem
import com.bf.climbinglogbook.repositories.GradesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddNewAscentViewModel @Inject constructor(
    gradeRepo: GradesRepository
) : ViewModel() {

    private val grades = gradeRepo.getGradesMap()

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
    val date: LiveData<Date> = _date

    private val _image = MutableLiveData<Uri>()
    val image: LiveData<Uri> = _image

    private val _selectedGradeOrdinal = MutableLiveData<Int>()
    val selectedGradeOrdinal: LiveData<Int> = _selectedGradeOrdinal

    private val _selectedAscentStyle = MutableLiveData<AscentStyle>()
    val selectedAscentStyle: LiveData<AscentStyle> = _selectedAscentStyle

    private val _country = MutableLiveData<String>()
    val country: LiveData<String> = _country

    private val _region = MutableLiveData<String>()
    val region: LiveData<String> = _region

    private val _rockName = MutableLiveData<String>()
    val rockName: LiveData<String> = _rockName

    private val _meters = MutableLiveData<Int>()
    val meters: LiveData<Int> = _meters

    private val _selectedClimbingType = MutableLiveData<ClimbingType>()
    val selectedClimbingType: LiveData<ClimbingType> = _selectedClimbingType

    private val _selectedBelayType = MutableLiveData<BelayType>()
    val selectedBelayType: LiveData<BelayType> = _selectedBelayType

    private val _numberOfPitches = MutableLiveData<Int>()
    val numberOfPitches: LiveData<Int> = _numberOfPitches

    private val _belayer = MutableLiveData<String>()
    val belayer: LiveData<String> = _belayer

    fun setGradeOrdinal(grade: Int) {
        if (grade == selectedGradeOrdinal.value) return
        if (grade < 0) return
        if (grade > (selectedGradesList.value?.size?.minus(1) ?: return)) return
        _selectedGradeOrdinal.value = grade
    }

    fun setImage(uri: Uri) {
        _image.value = uri
    }


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

    fun setAscentStyle(newAscentStyle: AscentStyle) {
        _selectedAscentStyle.value = newAscentStyle
    }

    fun setCountry(country: String) {
        _country.value = country
    }

    fun setRegion(region: String) {
        _region.value = region
    }

    fun setRockName(name: String) {
        _rockName.value = name
    }

    fun setMeters(length: Int) {
        _meters.value = length
    }

    fun setClimbingType(type: ClimbingType) {
        _selectedClimbingType.value = type
    }

    fun setBelayType(type: BelayType) {
        _selectedBelayType.value = type
    }

    fun setNumberOfPitches(number: Int) {
        _numberOfPitches.value = number
    }

    fun setBelayer(name: String) {
        _belayer.value = name
    }

}