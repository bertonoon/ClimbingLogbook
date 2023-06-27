package com.bf.climbinglogbook.ui.home

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bf.climbinglogbook.databinding.FragmentHomeBinding
import com.bf.climbinglogbook.db.Ascent
import com.bf.climbinglogbook.models.AscentStyle
import com.bf.climbinglogbook.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val mainViewModel: MainViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val numberOfAscents = mainViewModel.numberOfAscents
        if (numberOfAscents != null) numberOfAscents.observe(viewLifecycleOwner) {
            binding.textHome.text = it.toString()
        } else binding.textHome.text = "Fail"

        mainViewModel.addNewAscent(
            Ascent(
                name = "test",
                ascentStyle = AscentStyle.FLASH,
            )
        )
    }


}