package com.bf.climbinglogbook.ui.addNewAscent

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bf.climbinglogbook.R
import com.bf.climbinglogbook.databinding.FragmentAddNewAscentBinding
import com.bf.climbinglogbook.models.AddAscentErrors
import com.bf.climbinglogbook.models.AscentStyle
import com.bf.climbinglogbook.models.BelayType
import com.bf.climbinglogbook.models.ClimbingType
import com.bf.climbinglogbook.models.GradeSystem
import com.bf.climbinglogbook.other.Constants
import com.bf.climbinglogbook.ui.MainViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.EasyPermissions
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@AndroidEntryPoint
class AddNewAscentFragment : Fragment() {

    private var _binding: FragmentAddNewAscentBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val addNewAscentViewModel: AddNewAscentViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    private val cal = Calendar.getInstance()
    private var correctDataAndClickedSave = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNewAscentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.toolbar.ivArrowBack.setOnClickListener {
            navController.navigateUp()
        }
        binding.toolbar.title.text = getString(R.string.add_new_ascent_title)
        setSpinners()
        initValues()
        initObservers()
        initListeners()
    }

    private fun initValues() {
        binding.etDate.setText(addNewAscentViewModel.date.value.toString())
        binding.spinnerGradeSystem.setText(
            resources.getString(R.string.kurtyka_grade_system), false
        )
    }


    private fun initObservers() {
        addNewAscentViewModel.apply {
            selectedGradesList.observe(viewLifecycleOwner) { setGradesPicker(it) }
            failMsg.observe(viewLifecycleOwner) { showErrorMsg(it) }
            date.observe(viewLifecycleOwner) { setDateInView(it) }
            image.observe(viewLifecycleOwner) { setImageInView(it) }
            selectedGradeOrdinal.observe(viewLifecycleOwner) {
                if (binding.numberPickerGrade.value != it) binding.numberPickerGrade.value = it
            }
            routeName.observe(viewLifecycleOwner) {
                if (binding.etName.text.toString() != it) binding.etName.setText(it)
            }
            selectedBaseGradeSystem.observe(viewLifecycleOwner) {
                if (binding.spinnerGradeSystem.text.toString() != it.getLabel(requireContext())) {
                    binding.spinnerGradeSystem.setText(
                        it.getLabel(requireContext()), false
                    )
                }
            }
            hardGradeToggle.observe(viewLifecycleOwner) {
                if (binding.toggleHard.isChecked != it) binding.toggleHard.isChecked = it
            }
            selectedAscentStyle.observe(viewLifecycleOwner) {
                if (binding.spinnerAscentStyle.text.toString() != it.getLabel(requireContext())) {
                    binding.spinnerAscentStyle.setText(
                        it.getLabel(requireContext()), false
                    )
                }
            }

            country.observe(viewLifecycleOwner) {
                if (binding.etCountry.text.toString() != it) binding.etCountry.setText(it)
            }

            region.observe(viewLifecycleOwner) {
                if (binding.etRegion.text.toString() != it) binding.etRegion.setText(it)
            }

            rockName.observe(viewLifecycleOwner) {
                if (binding.etRock.text.toString() != it) binding.etRock.setText(it)
            }

            meters.observe(viewLifecycleOwner) {
                if (binding.etMeters.text.toString() != it.toString()) binding.etMeters.setText(it.toString())
            }

            selectedClimbingType.observe(viewLifecycleOwner) {
                if (binding.spinnerClimbingStyle.text.toString() != it.getLabel(requireContext())) {
                    binding.spinnerClimbingStyle.setText(
                        it.getLabel(requireContext()), false
                    )
                }
            }

            selectedBelayType.observe(viewLifecycleOwner) {
                if (binding.spinnerBelayType.text.toString() != it.getLabel(requireContext())) {
                    binding.spinnerBelayType.setText(
                        it.getLabel(requireContext()), false
                    )
                }
            }

            numberOfPitches.observe(viewLifecycleOwner) {
                if (binding.etPitches.text.toString() != it.toString()) binding.etPitches.setText(it.toString())
            }

            belayer.observe(viewLifecycleOwner) {
                if (binding.etBelayer.text.toString() != it) binding.etBelayer.setText(it)
            }

            successAdd.observe(viewLifecycleOwner) {
                if (correctDataAndClickedSave && it) {
                    showSnackBar(getString(R.string.add_new_ascent_save_correct))
                    navController.navigateUp()
                }
            }

        }
    }

    private fun setImageInView(uri: Uri?) {
        if (uri != null) binding.ivPhoto.visibility = View.VISIBLE
        Glide.with(requireContext()).load(uri).centerCrop().into(binding.ivPhoto)
    }

    private fun initListeners() {
        binding.etName.addTextChangedListener {
            addNewAscentViewModel.setRouteName(binding.etName.text.toString())
        }

        initCalendar()

        binding.toggleHard.setOnCheckedChangeListener { _, isChecked ->
            addNewAscentViewModel.setHardGradeToggle(isChecked)
        }
        binding.btnSave.setOnClickListener {
            correctDataAndClickedSave = addNewAscentViewModel.save()
        }
        binding.toolbar.addImage.setOnClickListener {
            choosePhotoFromGallery()
        }
        binding.ivPhoto.setOnClickListener {
            choosePhotoFromGallery()
        }

        binding.spinnerGradeSystem.setOnItemClickListener { adapterView, view, i, l ->
            val newBaseGradeSystem = when (i) {
                0 -> GradeSystem.FRENCH
                1 -> GradeSystem.KURTYKA
                2 -> GradeSystem.USA
                3 -> GradeSystem.UIAA
                else -> GradeSystem.FRENCH
            }
            addNewAscentViewModel.setBaseGradeSystem(newBaseGradeSystem)
            setGradesPicker(addNewAscentViewModel.selectedGradesList.value)
        }

        binding.spinnerAscentStyle.setOnItemClickListener { adapterView, view, i, l ->
            val newAscentStyle = when (i) {
                0 -> AscentStyle.ON_SIGHT
                1 -> AscentStyle.REDPOINT
                2 -> AscentStyle.FLASH
                3 -> AscentStyle.PINKPOINT
                4 -> AscentStyle.GREENPOINT
                else -> AscentStyle.REDPOINT
            }
            addNewAscentViewModel.setAscentStyle(newAscentStyle)
        }

        binding.etCountry.addTextChangedListener {
            addNewAscentViewModel.setCountry(
                binding.etCountry.text.toString()
            )
        }

        binding.etRegion.addTextChangedListener {
            addNewAscentViewModel.setRegion(
                binding.etRegion.text.toString()
            )
        }

        binding.etRock.addTextChangedListener {
            addNewAscentViewModel.setRockName(
                binding.etRock.text.toString()
            )
        }

        binding.etMeters.addTextChangedListener {
            val meters = binding.etMeters.text.toString()
            if (meters.isNotEmpty()) addNewAscentViewModel.setMeters(meters.toInt())

        }

        binding.spinnerClimbingStyle.setOnItemClickListener { adapterView, view, i, l ->
            val newClimbingType = when (i) {
                0 -> ClimbingType.SPORT
                1 -> ClimbingType.TRAD
                2 -> ClimbingType.BOULDERING
                3 -> ClimbingType.DRYTOOLING
                4 -> ClimbingType.MIX
                5 -> ClimbingType.ALPINE
                6 -> ClimbingType.ICE
                else -> ClimbingType.SPORT
            }
            addNewAscentViewModel.setClimbingType(newClimbingType)
        }

        binding.spinnerBelayType.setOnItemClickListener { adapterView, view, i, l ->
            val newBelayType = when (i) {
                0 -> BelayType.LEAD
                1 -> BelayType.TOP_ROPE
                2 -> BelayType.SOLO_LEAD
                3 -> BelayType.SOLO_TOP_ROPE
                4 -> BelayType.FREE_SOLO
                else -> BelayType.LEAD
            }
            addNewAscentViewModel.setBelayType(newBelayType)
        }

//        binding.etPitches.addTextChangedListener {
//            addNewAscentViewModel.setNumberOfPitches(
//                binding.etPitches.text.toString().toInt()
//            )
//        }

        binding.etBelayer.addTextChangedListener {
            addNewAscentViewModel.setBelayer(
                binding.etBelayer.text.toString()
            )
        }

    }

    private fun initCalendar() {
        dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            //setDateInView(cal.time)
            addNewAscentViewModel.setDate(cal.time)

        }

        binding.etDate.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setDateInView(date: Date) {
        val myFormat = Constants.DATE_FORMAT
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        binding.etDate.setText(sdf.format(date).toString())
    }

    private fun setSpinner(arrayTable: Int, spinner: AutoCompleteTextView) {
        ArrayAdapter.createFromResource(
            requireContext(), arrayTable, R.layout.add_ascent_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.add_ascent_spinner_style)
            spinner.threshold = 0
            spinner.setAdapter(adapter)
            spinner.setOnClickListener {
                spinner.showDropDown()
            }
        }
    }

    private fun setSpinners() {
        setSpinner(R.array.grade_systems, binding.spinnerGradeSystem)
        setSpinner(R.array.ascent_style, binding.spinnerAscentStyle)
        setSpinner(R.array.belay_type, binding.spinnerBelayType)
        setSpinner(R.array.climbing_style, binding.spinnerClimbingStyle)

    }

    private fun setGradesPicker(displayedValue: List<String>?) {
        if (displayedValue.isNullOrEmpty()) return

        val displayedValueArray = displayedValue.toTypedArray()

        binding.numberPickerGrade.apply {
            displayedValues = null
            minValue = 0
            maxValue = displayedValueArray.size - 1
            displayedValues = displayedValueArray

            setOnValueChangedListener { _, _, newVal ->
                addNewAscentViewModel.setGradeOrdinal(newVal)
            }
        }
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG).show()
    }

    private fun showErrorMsg(error: AddAscentErrors) {
        showSnackBar(
            when (error) {
                AddAscentErrors.NONE -> return
                AddAscentErrors.NULL_OR_EMPTY_NAME -> getString(R.string.add_ascent_name_null_or_empty_msg)
                AddAscentErrors.TO_LONG_NAME -> getString(R.string.add_ascent_name_to_long)
                AddAscentErrors.NULL_DATE -> getString(R.string.add_ascent_date_null_msg)
                AddAscentErrors.DATE_FROM_FUTURE -> getString(R.string.add_ascent_date_from_future_msg)
                AddAscentErrors.NULL_GRADE_SYSTEM -> getString(R.string.add_ascent_grade_system_null_msg)
            }
        )
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val imageUri = data?.data
                if (imageUri != null) {
                    addNewAscentViewModel.setImage(imageUri)
                    val bitmap = getBitmap(requireContext().contentResolver, imageUri)
                    if (bitmap != null) addNewAscentViewModel.setBitmap(bitmap)
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.add_ascent_failed_image_load),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    private fun getBitmap(contentResolver: ContentResolver, fileUri: Uri?): Bitmap? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, fileUri!!))
            } else {
                MediaStore.Images.Media.getBitmap(contentResolver, fileUri)
            }
        } catch (e: Exception) {
            null
        }
    }


    private fun choosePhotoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncher.launch(intent)
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}