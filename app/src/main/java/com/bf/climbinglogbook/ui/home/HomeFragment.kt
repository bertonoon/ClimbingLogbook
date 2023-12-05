package com.bf.climbinglogbook.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bf.climbinglogbook.R
import com.bf.climbinglogbook.adapters.AscentAdapter
import com.bf.climbinglogbook.databinding.FragmentHomeBinding
import com.bf.climbinglogbook.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val mainViewModel: MainViewModel by activityViewModels()
    private val binding get() = _binding!!
    private lateinit var ascentAdapter: AscentAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val popUpMenu = PopupMenu(requireContext(), binding.toolbar.ivMenu)
        popUpMenu.inflate(R.menu.home_fragment_menu)

        binding.toolbar.apply {
            title.text = getString(R.string.title_home)
            ivGradeCalc.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_home_to_gradeCalcFragment)
            }
            ivMenu.setOnClickListener {
                popUpMenu.show()
            }
        }

        setupRecyclerView()
        mainViewModel.lastThreeAscents?.observe(viewLifecycleOwner) {
            ascentAdapter.submitList(it)
        }

        val numberOfAscents = mainViewModel.numberOfAscents
        if (numberOfAscents != null) numberOfAscents.observe(viewLifecycleOwner)
        {
            binding.tvRoutesInDb.text = it.toString()
        } else binding.tvRoutesInDb.text = "Fail"


    }

    private fun setupRecyclerView() = binding.rvAscents.apply {
        ascentAdapter = AscentAdapter(mainViewModel)
        adapter = ascentAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }


}