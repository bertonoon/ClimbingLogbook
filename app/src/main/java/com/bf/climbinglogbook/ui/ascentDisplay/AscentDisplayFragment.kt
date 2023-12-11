package com.bf.climbinglogbook.ui.ascentDisplay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bf.climbinglogbook.databinding.FragmentAscentDisplayBinding
import com.bf.climbinglogbook.db.Ascent
import com.bf.climbinglogbook.other.Constants
import com.bf.climbinglogbook.repositories.GradesRepository
import com.bf.climbinglogbook.ui.MainViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class AscentDisplayFragment : Fragment() {

    private var _binding: FragmentAscentDisplayBinding? = null
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var navController: NavController
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAscentDisplayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.toolbar.ivArrowBack.setOnClickListener {
            navController.navigateUp()
        }
        initObservers()
    }

    private fun initObservers() {
        mainViewModel.ascentToDisplay.observe(viewLifecycleOwner) {
            setAscentToDisplay(it)
        }
    }

    private fun setAscentToDisplay(ascent: Ascent?) {

        if (ascent == null) return

        val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())
        val grades = GradesRepository().getGradesMap()
        val grade = grades[ascent.originalGradeSystem]?.get(ascent.originalGradeOrdinal)
        val meters = "${ascent.meters}m"

        var secondTitle = ascent.country?.capitalize(Locale.getDefault()) ?: ""
        if (!ascent.region.isNullOrEmpty()) {
            secondTitle += "/${ascent.region?.capitalize(Locale.getDefault())}"
        }
        if (!ascent.rock.isNullOrEmpty()) {
            secondTitle += "/${ascent.rock?.capitalize(Locale.getDefault())}"
        }
        if (secondTitle.startsWith("/")) {
            secondTitle = secondTitle.removeRange(0..0)
        }

        binding.apply {
            toolbar.title.text = ascent.name.capitalize(Locale.getDefault())
            tvTitle.text = ascent.name.capitalize(Locale.getDefault())
            if (secondTitle.isNotEmpty()) {
                tvSecondTitle.visibility = View.VISIBLE
                tvSecondTitle.text = secondTitle
            }
            tvDate.text = ascent.date.let { dateFormat.format(it) }
            tvGrade.text = grade
            tvClimbingType.text = ascent.climbingType?.getLabel(requireContext()) ?: ""
            tvStyle.text = ascent.ascentStyle.getLabel(requireContext())
            tvMeters.text = meters
            tvBelayType.text = ascent.belayType?.getLabel(requireContext()) ?: ""
            tvBelayer.text = ascent.belayer?.capitalize(Locale.getDefault()) ?: ""
            if (!ascent.comment.isNullOrEmpty()) tvComment.text = ascent.comment
            else tvComment.visibility = View.INVISIBLE
            if (ascent.img != null) setImageInView(ascent.img!!)
        }
    }

    private fun setImageInView(path: String) {
        if (path.isEmpty()) return
        val file = File(path)
        binding.ivPhoto.visibility = View.VISIBLE
        Glide.with(requireContext()).load(file).fitCenter().into(binding.ivPhoto)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}