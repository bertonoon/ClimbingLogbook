package com.bf.climbinglogbook.ui.gradeCalc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bf.climbinglogbook.databinding.FragmentGradeCalcBinding
import com.bf.climbinglogbook.models.gradeEnums.KurtykaEnum
import com.bf.climbinglogbook.models.gradeEnums.USAEnum
import com.bf.climbinglogbook.other.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.util.Arrays
import java.util.EnumMap

@AndroidEntryPoint
class GradeCalcFragment : Fragment() {

    private var _binding: FragmentGradeCalcBinding? = null
    private val binding get() = _binding!!

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
        binding.sourceNumberPicker.apply {
            minValue = Constants.GRADE_ENUM_MIN
            maxValue = Constants.GRADE_ENUM_MAX
            displayedValues = USAEnum.getList().toTypedArray()
            setOnValueChangedListener { _, _, newVal ->
                binding.textHome.text = KurtykaEnum.numberToGrade(newVal).toString()
            }
        }
    }


}