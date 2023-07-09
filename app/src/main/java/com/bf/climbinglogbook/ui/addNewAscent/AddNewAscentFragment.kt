package com.bf.climbinglogbook.ui.addNewAscent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bf.climbinglogbook.R
import com.bf.climbinglogbook.databinding.FragmentAddNewAscentBinding
import com.bf.climbinglogbook.models.GradeSystem
import com.bf.climbinglogbook.ui.gradeCalc.GradeCalcViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddNewAscentFragment : Fragment() {

    private var _binding: FragmentAddNewAscentBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val addNewAscentViewModel: AddNewAscentViewModel by viewModels()


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
        setSpinners()
    }



    private fun setSpinners(){
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.grade_systems,
            R.layout.custom_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.custom_spinner_style)
            binding.spinnerGradeSystem.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.ascent_style,
            R.layout.custom_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.custom_spinner_style)
            binding.spinnerAscentStyle.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.belay_type,
            R.layout.custom_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.custom_spinner_style)
            binding.spinnerBelayType.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.climbing_style,
            R.layout.custom_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.custom_spinner_style)
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


}