package com.bf.climbinglogbook.ui.gradeCalc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bf.climbinglogbook.R
import com.bf.climbinglogbook.databinding.FragmentGradeCalcBinding
import com.bf.climbinglogbook.models.GradeSystem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GradeCalcFragment : Fragment() {

    private var _binding: FragmentGradeCalcBinding? = null
    private val binding get() = _binding!!
    private val gradeCalcViewModel: GradeCalcViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGradeCalcBinding.inflate(inflater, container, false)
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
        binding.toolbar.title.text = getString(R.string.grade_calculator_title)

        gradeCalcViewModel.gradesMap.observe(viewLifecycleOwner) {
            setNewCalcResult(
                it[GradeSystem.FRENCH] ?: "",
                it[GradeSystem.KURTYKA] ?: "",
                it[GradeSystem.USA] ?: "",
                it[GradeSystem.UIAA] ?: "",
            )
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.grade_systems,
            R.layout.custom_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.custom_spinner_style)
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
                    gradeCalcViewModel.setBaseGradeSystem(newBaseGradeSystem)
                    setGradesPicker(gradeCalcViewModel.grades.value)
                    gradeCalcViewModel.convertGrade(binding.sourceNumberPicker.value)

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}

            }

        binding.toggleHard.setOnCheckedChangeListener { _, isChecked ->
            gradeCalcViewModel.setHardGradeToggle(isChecked)
            gradeCalcViewModel.convertGrade(binding.sourceNumberPicker.value)
        }

        gradeCalcViewModel.convertGrade(binding.sourceNumberPicker.value)
    }

    private fun setGradesPicker(displayedValue: List<String>?) {
        if (displayedValue.isNullOrEmpty()) return

        val displayedValueArray = displayedValue.toTypedArray()

        binding.sourceNumberPicker.apply {
            displayedValues = null
            minValue = 0
            maxValue = displayedValueArray.size - 1
            displayedValues = displayedValueArray

            setOnValueChangedListener { _, _, newVal ->
                gradeCalcViewModel.convertGrade(newVal)
            }
        }
    }

    private fun setNewCalcResult(french: String, kurtyka: String, usa: String, uiaa: String) {
        binding.tvFrenchResult.text = french
        binding.tvKurtykaResult.text = kurtyka
        binding.tvUsaResult.text = usa
        binding.tvUiaaResult.text = uiaa
    }


}