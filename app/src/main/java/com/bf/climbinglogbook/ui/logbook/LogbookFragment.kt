package com.bf.climbinglogbook.ui.logbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bf.climbinglogbook.databinding.FragmentLogbookBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class LogbookFragment : Fragment() {

    private var _binding: FragmentLogbookBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val logbookViewModel =
            ViewModelProvider(this).get(LogbookViewModel::class.java)

        _binding = FragmentLogbookBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        logbookViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}