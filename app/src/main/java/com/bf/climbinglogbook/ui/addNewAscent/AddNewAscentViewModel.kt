package com.bf.climbinglogbook.ui.addNewAscent

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bf.climbinglogbook.db.Ascent
import com.bf.climbinglogbook.models.AddAscentErrors
import com.bf.climbinglogbook.models.AscentStyle
import com.bf.climbinglogbook.models.BelayType
import com.bf.climbinglogbook.models.ClimbingType
import com.bf.climbinglogbook.models.GradeSystem
import com.bf.climbinglogbook.other.Constants
import com.bf.climbinglogbook.repositories.GradesRepository
import com.bf.climbinglogbook.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class AddNewAscentViewModel @Inject constructor(
    gradeRepo: GradesRepository,
    val mainRepo: MainRepository
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

    private val _bitmap = MutableLiveData<Bitmap>()
    val bitmap: LiveData<Bitmap> = _bitmap

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

    fun setDate(date: Date) {
        _date.value = date
        Log.i("setDate", this.date.toString())
    }

    private fun validateName(): Boolean {
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

    private fun validateDate(): Boolean {
        val date = date.value

        if (date == null) {
            _failMsg.value = AddAscentErrors.NULL_DATE
            return false
        }

        val sdf = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())

        if (date > Date()) {
            _failMsg.value = AddAscentErrors.DATE_FROM_FUTURE
            Log.i("DateCheck", "przyszlosc")
            return false
        }
        Log.i("DateCheck", "Po sprawdzeniu")
        return true
    }

    private fun validateGradeSystem(): Boolean {
        val system = selectedBaseGradeSystem.value

        if (system == null) {
            _failMsg.value = AddAscentErrors.NULL_GRADE_SYSTEM
            return false
        }
        return true
    }

    private fun validate(): Boolean {
        return validateName() &&
                validateDate() &&
                validateGradeSystem()
    }


    fun save(): Boolean {

        if (!validate()) return false

        val newAscent = Ascent(
            name = routeName.value!!,
            img = bitmap.value,
            date = date.value!!,
            gradeSystem = selectedBaseGradeSystem.value!!,
            hard = hardGradeToggle.value ?: false,
            gradeOrdinal = selectedGradeOrdinal.value!!,
            ascentStyle = selectedAscentStyle.value!!,
            country = country.value,
            region = region.value,
            rock = rockName.value,
            lat = 0L,
            lng = 0L, //TODO Ascent Localization
            meters = meters.value ?: 0,
            climbingType = selectedClimbingType.value,
            belayType = selectedBelayType.value,
            pitches = numberOfPitches.value ?: 1,
            belayer = belayer.value,
            comment = "" // TODO Comment
        )
        return addNewAscentToDB(newAscent)
    }

    private fun addNewAscentToDB(ascent: Ascent): Boolean {
        var result = false
        viewModelScope.launch(Dispatchers.IO) {
            result = mainRepo.insertAscent(ascent)
        }
        return result
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

    fun setBitmap(bitmap: Bitmap) {
        _bitmap.value = bitmap
    }

}