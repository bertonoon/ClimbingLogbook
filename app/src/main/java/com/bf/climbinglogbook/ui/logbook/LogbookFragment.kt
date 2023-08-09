package com.bf.climbinglogbook.ui.logbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bf.climbinglogbook.R
import com.bf.climbinglogbook.databinding.FragmentLogbookBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint

class LogbookFragment : Fragment() {

    private var _binding: FragmentLogbookBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController : NavController

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}