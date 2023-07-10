package com.bf.climbinglogbook.ui.addNewAscent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.Constraints
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bf.climbinglogbook.R
import com.bf.climbinglogbook.databinding.FragmentAddNewAscentBinding
import com.bf.climbinglogbook.models.AddAscentErrors
import com.bf.climbinglogbook.models.GradeSystem
import com.bf.climbinglogbook.other.Constants
import com.bf.climbinglogbook.ui.gradeCalc.GradeCalcViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class AddNewAscentFragment : Fragment() {

    private var _binding: FragmentAddNewAscentBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val addNewAscentViewModel: AddNewAscentViewModel by viewModels()
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    private val cal = Calendar.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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

        initValues()
        setSpinners()
        initObservers()
        initListeners()
    }

    private fun initValues() {
        binding.etName.setText(addNewAscentViewModel.routeName.value ?: "")
        binding.etDate.setText(addNewAscentViewModel.date.value.toString())
    }


    private fun initObservers() {
        addNewAscentViewModel.apply {
            selectedGradesList.observe(viewLifecycleOwner) { setGradesPicker(it) }
            failMsg.observe(viewLifecycleOwner) { showErrorMsg(it) }
            date.observe(viewLifecycleOwner) { setDateInView(it) }
        }
    }

    private fun setRouteNameInView(name: String?) {
        if (name.isNullOrEmpty()) return
        binding.etName.setText(name.toString()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() })
    }

    private fun initListeners() {
        binding.etName.addTextChangedListener {
            addNewAscentViewModel.setRouteName(binding.etName.text.toString())
        }
        binding.toggleHard.setOnCheckedChangeListener { _, isChecked ->
            addNewAscentViewModel.setHardGradeToggle(isChecked)
        }
        binding.btnSave.setOnClickListener {
            addNewAscentViewModel.save()
        }
        initCalendar()

    }

    private fun initCalendar() {
        dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                setDateInView(cal.time)
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

    private fun setSpinners() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.grade_systems,
            R.layout.add_ascent_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.add_ascent_spinner_style)
            binding.spinnerGradeSystem.adapter = adapter
        }

        binding.spinnerGradeSystem.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    pos: Int,
                    id: Long
                ) {
                    val newBaseGradeSystem = when (pos) {
                        0 -> GradeSystem.FRENCH
                        1 -> GradeSystem.KURTYKA
                        2 -> GradeSystem.USA
                        3 -> GradeSystem.UIAA
                        else -> GradeSystem.FRENCH
                    }
                    addNewAscentViewModel.setBaseGradeSystem(newBaseGradeSystem)
                    setGradesPicker(addNewAscentViewModel.selectedGradesList.value)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.ascent_style,
            R.layout.add_ascent_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.add_ascent_spinner_style)
            binding.spinnerAscentStyle.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.belay_type,
            R.layout.add_ascent_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.add_ascent_spinner_style)
            binding.spinnerBelayType.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.climbing_style,
            R.layout.add_ascent_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.add_ascent_spinner_style)
            binding.spinnerClimbingStyle.adapter = adapter
        }
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
                //
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
            }
        )
    }


}