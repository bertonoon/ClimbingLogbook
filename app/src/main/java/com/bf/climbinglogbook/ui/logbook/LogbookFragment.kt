package com.bf.climbinglogbook.ui.logbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bf.climbinglogbook.R
import com.bf.climbinglogbook.adapters.AscentAdapter
import com.bf.climbinglogbook.databinding.FragmentLogbookBinding
import com.bf.climbinglogbook.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class LogbookFragment : Fragment() {

    private var _binding: FragmentLogbookBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val mainViewModel: MainViewModel by viewModels()
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
        mainViewModel.allAscents?.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.tvNoRecordsInDb.visibility = View.VISIBLE
                binding.rvAscents.visibility = View.GONE
            } else {
                ascentAdapter.submitList(it)
                binding.tvNoRecordsInDb.visibility = View.INVISIBLE
                binding.rvAscents.visibility = View.VISIBLE
            }
        }
    }

    private fun initListeners() {
        binding.apply {
            toolbar.ivSort.setOnClickListener {
                binding.hsvSortOptions.visibility = when (binding.hsvSortOptions.visibility){
                    View.VISIBLE -> View.GONE
                    View.GONE ->View.VISIBLE
                    else -> View.GONE
                }
            }
            toolbar.ivSearch.setOnClickListener {
                binding.etSearchBar.visibility = when (binding.etSearchBar.visibility){
                    View.VISIBLE -> View.GONE
                    View.GONE -> View.VISIBLE
                    else -> View.GONE
                }
            }

        }
    }

}