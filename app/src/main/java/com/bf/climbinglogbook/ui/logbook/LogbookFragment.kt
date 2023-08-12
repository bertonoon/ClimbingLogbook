package com.bf.climbinglogbook.ui.logbook

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bf.climbinglogbook.R
import com.bf.climbinglogbook.adapters.AscentAdapter
import com.bf.climbinglogbook.databinding.FragmentLogbookBinding
import com.bf.climbinglogbook.models.SortType
import com.bf.climbinglogbook.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class LogbookFragment : Fragment() {

    private var _binding: FragmentLogbookBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val mainViewModel: MainViewModel by viewModels()
    private val logbookViewModel: LogbookViewModel by viewModels()
    private lateinit var ascentAdapter: AscentAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val logbookViewModel =
            ViewModelProvider(this).get(LogbookViewModel::class.java)

        _binding = FragmentLogbookBinding.inflate(inflater, container, false)


        binding.toolbar.title.text = getString(R.string.title_logbook)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.fabNewAscent.setOnClickListener {
            navController.navigate(R.id.action_navigation_logbook_to_addNewAscent)
        }

        setupRecyclerView()
        initObservers()
        initListeners()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() = binding.rvAscents.apply {
        ascentAdapter = AscentAdapter()
        adapter = ascentAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initObservers() {
        logbookViewModel.ascents.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.tvNoRecordsInDb.visibility = View.VISIBLE
                binding.rvAscents.visibility = View.GONE
            } else {
                ascentAdapter.submitList(it)
                binding.tvNoRecordsInDb.visibility = View.INVISIBLE
                binding.rvAscents.visibility = View.VISIBLE
            }
        }
        logbookViewModel.sortType.observe(viewLifecycleOwner) {
            setSuitableSortChipColor(it)
        }
    }

    private fun setSuitableSortChipColor(sortType: SortType) {
        setAllChipColorsToDefault()
        when (sortType) {
            SortType.DATE -> binding.chipDate.chipBackgroundColor = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.palette_4
                )
            )

            SortType.NAME -> binding.chipName.chipBackgroundColor = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.palette_4
                )
            )

            SortType.GRADE -> binding.chipGrade.chipBackgroundColor = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.palette_4
                )
            )

            SortType.STYLE -> binding.chipStyle.chipBackgroundColor = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.palette_4
                )
            )

            SortType.METERS -> binding.chipMeters.chipBackgroundColor = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.palette_4
                )
            )
        }
    }

    private fun setAllChipColorsToDefault() {
        binding.apply {
            chipDate.chipBackgroundColor = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            chipName.chipBackgroundColor = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            chipGrade.chipBackgroundColor = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            chipStyle.chipBackgroundColor = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            chipMeters.chipBackgroundColor = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )

        }
    }

    private fun initListeners() {
        binding.apply {
            toolbar.ivSort.setOnClickListener {
                binding.hsvSortOptions.visibility = when (binding.hsvSortOptions.visibility) {
                    View.VISIBLE -> View.GONE
                    View.GONE -> View.VISIBLE
                    else -> View.GONE
                }
            }
            toolbar.ivSearch.setOnClickListener {
                binding.etSearchBar.visibility = when (binding.etSearchBar.visibility) {
                    View.VISIBLE -> View.GONE
                    View.GONE -> View.VISIBLE
                    else -> View.GONE
                }
            }
            chipDate.setOnClickListener {
                logbookViewModel.sortAscents(SortType.DATE)
            }
            chipName.setOnClickListener {
                logbookViewModel.sortAscents(SortType.NAME)
            }
            chipGrade.setOnClickListener {
                logbookViewModel.sortAscents(SortType.GRADE)
            }
            chipMeters.setOnClickListener {
                logbookViewModel.sortAscents(SortType.METERS)
            }
            chipStyle.setOnClickListener {
                logbookViewModel.sortAscents(SortType.STYLE)
            }
        }
    }


}