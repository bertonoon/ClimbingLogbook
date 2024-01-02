package com.bf.climbinglogbook.ui.addNewAscent

import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
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
import com.bf.climbinglogbook.repositories.MainRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AddNewAscentViewModel @Inject constructor(
    gradeRepo: GradesRepository,
    val mainRepo: MainRepositoryInterface
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

    private val _routeName = MutableLiveData<String>().apply {
        value = ""
    }
    val routeName: LiveData<String> = _routeName

    private val _failMsg = MutableLiveData<AddAscentErrors>().apply {
        value = AddAscentErrors.NONE
    }
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

    private val _country = MutableLiveData<String>().apply {
        value = ""
    }
    val country: LiveData<String> = _country

    private val _region = MutableLiveData<String>().apply {
        value = ""
    }
    val region: LiveData<String> = _region

    private val _rockName = MutableLiveData<String>().apply {
        value = ""
    }
    val rockName: LiveData<String> = _rockName

    private val _meters = MutableLiveData<Int>()
    val meters: LiveData<Int> = _meters

    private val _selectedClimbingType = MutableLiveData<ClimbingType>()
    val selectedClimbingType: LiveData<ClimbingType> = _selectedClimbingType

    private val _selectedBelayType = MutableLiveData<BelayType>()
    val selectedBelayType: LiveData<BelayType> = _selectedBelayType

    private val _numberOfPitches = MutableLiveData<Int>().apply {
        value = 1
    }
    val numberOfPitches: LiveData<Int> = _numberOfPitches

    private val _belayer = MutableLiveData<String>().apply {
        value = ""
    }
    val belayer: LiveData<String> = _belayer

    private val _successAdd = MutableLiveData<Boolean>()
    val successAdd: LiveData<Boolean> = _successAdd

    // SAVE TEST VALUES
//    init {
//        Log.i("AddNewAscent","Init")
//        if ((mainRepo.numberOfItemsInDB()?.value ?: 0) < 1) {
//            Log.i("AddNewAscent", "getList")
//            val testAscents = TestAscents.getList()
//            for (ascent in testAscents) {
//                addNewAscentToDB(ascent)
//            }
//        }
//    }


    fun setGradeOrdinal(grade: Int): Boolean {
        if (grade == selectedGradeOrdinal.value) return false
        if (grade < 0) return false
        if (grade > (selectedGradesList.value?.size?.minus(1) ?: return false)) return false
        _selectedGradeOrdinal.value = grade
        return true
    }

    fun setImage(uri: Uri): Boolean {
        _image.value = uri
        return true
    }

    fun setBaseGradeSystem(newBaseGradeSystem: GradeSystem): Boolean {
        if (newBaseGradeSystem == _selectedBaseGradeSystem.value) return false
        _selectedBaseGradeSystem.value = newBaseGradeSystem
        setGrades()
        return true
    }

    fun setDate(date: Date?): Boolean {
        if (date == null) return false
        if (date.after(Date())) {
            _failMsg.postValue(AddAscentErrors.DATE_FROM_FUTURE)
        }
        _date.value = date
        return true
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

        val imgPath = saveBitmapInStorage()

        val newAscent = Ascent(
            name = routeName.value!!,
            img = imgPath,
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
        //val kurtykaGrade = KurtykaGrade.numberToGrade(gradeNum, hard) ?: return -1
        val kurtykaGrade = KurtykaGrade.entries[ordinal]
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


    fun setRouteName(name: String): Boolean {
        return if (validateStringField(name)) {
            _routeName.value = name
            true
        } else {
            false
        }
    }

    private fun validateStringField(str: String): Boolean {
        if (str.isEmpty()) return false
        return str.length <= 50
    }

    fun setHardGradeToggle(checked: Boolean): Boolean {
        if (checked == hardGradeToggle.value) return false
        _hardGradeToggle.value = checked
        return true
    }

    private fun setGrades() {
        _selectedGradesList.value =
            grades[selectedBaseGradeSystem.value] ?: grades[GradeSystem.KURTYKA]
    }

    fun setAscentStyle(newAscentStyle: AscentStyle): Boolean {
        if (newAscentStyle == selectedAscentStyle.value) return false
        _selectedAscentStyle.value = newAscentStyle
        return true
    }

    fun setCountry(country: String): Boolean {
        return if (validateStringField(country)) {
            _country.value = country
            true
        } else {
            false
        }
    }

    fun setRegion(region: String): Boolean {
        return if (validateStringField(region)) {
            _region.value = region
            true
        } else {
            false
        }
    }

    fun setRockName(name: String): Boolean {
        return if (validateStringField(name)) {
            _rockName.value = name
            true
        } else {
            false
        }
    }

    fun setMeters(length: Int): Boolean {
        if (length < 0 || length > 99999) return false
        _meters.value = length
        return true
    }

    fun setClimbingType(type: ClimbingType): Boolean {
        if (type == selectedClimbingType.value) return false
        _selectedClimbingType.value = type
        return true
    }

    fun setBelayType(type: BelayType): Boolean {
        if (type == selectedBelayType.value) return false
        _selectedBelayType.value = type
        return true
    }

    fun setNumberOfPitches(number: Int) {
        _numberOfPitches.value = number
    }

    fun setBelayer(name: String): Boolean {
        return if (validateStringField(name)) {
            _belayer.value = name
            true
        } else {
            false
        }
    }

    fun setBitmap(bitmap: Bitmap) {
        _bitmap.value = bitmap
    }

    private fun saveBitmapInStorage(): String? {
        if (bitmap.value == null) return null

        try {
            val sdf = SimpleDateFormat(Constants.IMG_KEY_FORMAT, Locale.getDefault())
            val dateString = sdf.format(date.value!!)

            val fileName = dateString + "_" + routeName.value
            val filePath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    .toString() + "/${Constants.DCIM_FOLDER_NAME}"
            File(filePath).mkdir()
            val pictureFile = File(filePath, "$fileName.${Constants.IMG_EXTENSION}")
            val outputStream = FileOutputStream(pictureFile)
            bitmap.value?.compress(
                Bitmap.CompressFormat.JPEG,
                Constants.IMG_COMPRESS_QUALITY,
                outputStream
            )
            outputStream.close()
            return "$filePath/$fileName.${Constants.IMG_EXTENSION}"
        } catch (e: FileNotFoundException) {
            Log.d("saveBitmap", "File not found: $e")
            return null
        } catch (e: IOException) {
            Log.d("saveBitmap", "Error accessing file: $e")
            return null
        }
    }

}