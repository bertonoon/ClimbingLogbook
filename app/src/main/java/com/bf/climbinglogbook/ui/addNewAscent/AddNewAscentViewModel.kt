package com.bf.climbinglogbook.ui.addNewAscent

import android.graphics.Bitmap
import android.net.Uri
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
import com.bf.climbinglogbook.models.gradeEnums.FrenchGrade
import com.bf.climbinglogbook.models.gradeEnums.KurtykaGrade
import com.bf.climbinglogbook.models.gradeEnums.UIAAGrade
import com.bf.climbinglogbook.models.gradeEnums.USAGrade
import com.bf.climbinglogbook.other.Constants
import com.bf.climbinglogbook.other.GradeConverters
import com.bf.climbinglogbook.repositories.GradesRepository
import com.bf.climbinglogbook.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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

    private val _selectedGradeOrdinal = MutableLiveData<Int>().apply {
        value = 0
    }
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

    private val _successAdd = MutableLiveData<Boolean>().apply {
        value = false
    }
    val successAdd: LiveData<Boolean> = _successAdd

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

        if (date > Date()) {
            _failMsg.value = AddAscentErrors.DATE_FROM_FUTURE
            return false
        }
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
                validateGradeSystem() &&
                validateAscentStyle()
    }

    private fun validateAscentStyle(): Boolean {
        if (selectedAscentStyle.value == null) {
            _failMsg.value = AddAscentErrors.NULL_ASCENT_STYLE
            return false
        }
        return true
    }


    fun save(): Boolean {

        if (!validate()) return false

        val usaGradeNumber = getUsaGradeNumber()
        if (usaGradeNumber < 0) return false.also {
            _failMsg.value = AddAscentErrors.ERROR_CONV_TO_USA
        }

        _failMsg.value = AddAscentErrors.NONE


        val newAscent = Ascent(
            name = routeName.value!!,
            img = bitmap.value,
            date = date.value!!,
            originalGradeSystem = selectedBaseGradeSystem.value!!,
            hard = hardGradeToggle.value ?: false,
            originalGradeOrdinal = selectedGradeOrdinal.value!!,
            usaGradeNumber = usaGradeNumber,
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

        addNewAscentToDB(newAscent)
        return true
    }

    private fun usaGradeNumberFromFrenchOrdinal(ordinal: Int, hard: Boolean): Int {
        val gradeNum = if (ordinal == 0) 1 else ordinal
        val frenchGrade = FrenchGrade.numberToGrade(gradeNum, hard) ?: return -1
        val usaGrade = GradeConverters().frenchToUsa(frenchGrade, hard) ?: return -1
        return USAGrade.gradeToNumber(usaGrade, hard)
    }

    private fun usaGradeNumberFromKurtykaOrdinal(ordinal: Int, hard: Boolean): Int {
        val gradeNum = if (ordinal == 0) 1 else ordinal
        val kurtykaGrade = KurtykaGrade.numberToGrade(gradeNum, hard) ?: return -1
        val usaGrade = GradeConverters().kurtykaToUsa(kurtykaGrade, hard) ?: return -1
        return USAGrade.gradeToNumber(usaGrade, hard)
    }

    private fun usaGradeNumberFromUiaaOrdinal(ordinal: Int, hard: Boolean): Int {
        val gradeNum = if (ordinal == 0) 1 else ordinal
        val uiaaGrade = UIAAGrade.numberToGrade(gradeNum, hard) ?: return -1
        val usaGrade = GradeConverters().uiaaToUsa(uiaaGrade, hard) ?: return -1
        return USAGrade.gradeToNumber(usaGrade, hard)
    }

    private fun getUsaGradeNumber(): Int {
        val ordinal = selectedGradeOrdinal.value
        val hard = hardGradeToggle.value

        if (ordinal == null || ordinal < 0) return -1
        if (hard == null) return -1

        return when (selectedBaseGradeSystem.value ?: return -1) {
            GradeSystem.FRENCH -> usaGradeNumberFromFrenchOrdinal(ordinal, hard)
            GradeSystem.KURTYKA -> usaGradeNumberFromKurtykaOrdinal(ordinal, hard)
            GradeSystem.USA -> ordinal
            GradeSystem.UIAA -> usaGradeNumberFromUiaaOrdinal(ordinal, hard)
        }
    }


    private fun addNewAscentToDB(ascent: Ascent) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepo.insertAscent(ascent)
            _successAdd.postValue(true)
        }
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