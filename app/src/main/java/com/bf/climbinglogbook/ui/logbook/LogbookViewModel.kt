package com.bf.climbinglogbook.ui.logbook

import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bf.climbinglogbook.ui.MainViewModel

class LogbookViewModel : ViewModel() {



    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}