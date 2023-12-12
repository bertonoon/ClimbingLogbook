package com.bf.climbinglogbook.ui.logbook

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bf.climbinglogbook.R
import com.bf.climbinglogbook.adapters.AscentAdapter
import com.bf.climbinglogbook.databinding.FragmentLogbookBinding
import com.bf.climbinglogbook.models.FilterType
import com.bf.climbinglogbook.models.LogbookMsg
import com.bf.climbinglogbook.models.SortType
import com.bf.climbinglogbook.ui.MainViewModel
import com.bf.climbinglogbook.utils.SwipeToDeleteCallback
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class LogbookFragment : Fragment() {

    private var _binding: FragmentLogbookBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val mainViewModel: MainViewModel by activityViewModels()
    private val logbookViewModel: LogbookViewModel by viewModels()
    private lateinit var ascentAdapter: AscentAdapter
    private var filterType = FilterType()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogbookBinding.inflate(inflater, container, false)
        binding.toolbar.title.text = getString(R.string.title_logbook)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.navigation_home)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
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
        ascentAdapter = AscentAdapter(mainViewModel)
        adapter = ascentAdapter
        layoutManager = LinearLayoutManager(requireContext())

        val deleteSwipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding.rvAscents.adapter as AscentAdapter
                val ascentToDelete = adapter.getItem(viewHolder.adapterPosition)
                Log.i("RvDelete", ascentToDelete.toString())
                logbookViewModel.deleteAscent(ascentToDelete)
            }
        }
        val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
        deleteItemTouchHelper.attachToRecyclerView(binding.rvAscents)

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
        logbookViewModel.msg.observe(viewLifecycleOwner) {
            showMsg(it)
        }
        logbookViewModel.filterType.observe(viewLifecycleOwner) {
            filterType = it
            binding.apply {
                chipNone.isChecked = it.none
                chipOS.isChecked = it.os
                chipRP.isChecked = it.rp
                chipFlash.isChecked = it.flash
                chipTrad.isChecked = it.trad
                chipSport.isChecked = it.sport
            }
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
            toolbar.ivFilter.setOnClickListener {
                binding.hsvFilterOptions.visibility = when (binding.hsvFilterOptions.visibility) {
                    View.VISIBLE -> View.GONE
                    View.GONE -> View.VISIBLE
                    else -> View.GONE
                }
            }
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
                logbookViewModel.changeSortDirection()
                logbookViewModel.sortAscentsWithFilterAndSearch(SortType.DATE)
            }
            chipName.setOnClickListener {
                logbookViewModel.changeSortDirection()
                logbookViewModel.sortAscentsWithFilterAndSearch(SortType.NAME)
            }
            chipGrade.setOnClickListener {
                logbookViewModel.changeSortDirection()
                logbookViewModel.sortAscentsWithFilterAndSearch(SortType.GRADE)
            }
            chipMeters.setOnClickListener {
                logbookViewModel.changeSortDirection()
                logbookViewModel.sortAscentsWithFilterAndSearch(SortType.METERS)
            }
            chipStyle.setOnClickListener {
                logbookViewModel.changeSortDirection()
                logbookViewModel.sortAscentsWithFilterAndSearch(SortType.STYLE)
            }
            etSearchBar.addTextChangedListener {
                logbookViewModel.search(binding.etSearchBar.text.toString())
            }
            chipGroupFilter.setOnCheckedStateChangeListener { _, _ ->
                filterAscents()
            }
        }
    }

    private fun filterAscents() {
        val newFilter = FilterType()
        newFilter.none = binding.chipNone.isChecked

        if (!filterType.none && newFilter.none) {
            clearFilter()
            logbookViewModel.filterAscent(newFilter)
            return
        }

        newFilter.os = binding.chipOS.isChecked
        newFilter.flash = binding.chipFlash.isChecked
        newFilter.rp = binding.chipRP.isChecked
        newFilter.trad = binding.chipTrad.isChecked
        newFilter.sport = binding.chipSport.isChecked

        if (newFilter.isFilterActive()) {
            newFilter.none = false
            binding.chipNone.isChecked = false
        } else {
            newFilter.none = true
            binding.chipNone.isChecked = true
        }

        logbookViewModel.filterAscent(newFilter)
        //ascentAdapter.notifyDataSetChanged()
    }

    private fun clearFilter() {
        binding.apply {
            chipNone.isChecked = true
            chipOS.isChecked = false
            chipFlash.isChecked = false
            chipRP.isChecked = false
            chipTrad.isChecked = false
            chipSport.isChecked = false
        }
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG).show()
    }

    private fun showMsg(msg: LogbookMsg) {
        showSnackBar(
            when (msg) {
                LogbookMsg.NONE -> return
                LogbookMsg.SUCCESSFULLY_DELETE_RECORD -> getString(R.string.logbook_ascent_deleted)
            }
        ).also {
            logbookViewModel.zeroMsg()
        }
    }
}