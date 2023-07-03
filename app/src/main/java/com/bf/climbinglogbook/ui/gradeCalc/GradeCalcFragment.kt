package com.bf.climbinglogbook.ui.gradeCalc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
    private lateinit var gradeCalcViewModel: GradeCalcViewModel
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
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.grade_systems,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
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
                    gradeCalcViewModel.selectedBaseGradeSystem.value = when (pos) {
                        0 -> GradeSystem.FRENCH
                        1 -> GradeSystem.KURTYKA
                        2 -> GradeSystem.USA
                        else -> GradeSystem.FRENCH
                    }
                    gradeCalcViewModel.setGrades()
                    initGradesPicker() //TODO ("crash after fast change french->kurtyka->french")
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}

            }

        gradeCalcViewModel = ViewModelProvider(this)[GradeCalcViewModel::class.java]
        //initGradesPicker()
        binding.sourceNumberPicker.apply {
            setOnValueChangedListener { _, _, newVal ->
                gradeCalcViewModel.convertGrade(newVal)
            }
        }
    }

    private fun initGradesPicker() {
        gradeCalcViewModel.grades.observe(viewLifecycleOwner) {
            binding.sourceNumberPicker.apply {
                minValue = 0
                maxValue = it.size - 1
                displayedValues = it.toTypedArray()
            }
        }
    }


}