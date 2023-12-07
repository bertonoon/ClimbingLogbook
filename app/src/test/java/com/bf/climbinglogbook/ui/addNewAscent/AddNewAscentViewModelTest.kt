package com.bf.climbinglogbook.ui.addNewAscent

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.content.pm.ApplicationInfoBuilder
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bf.climbinglogbook.db.AscentDAO
import com.bf.climbinglogbook.db.LogbookDatabase
import com.bf.climbinglogbook.repositories.FakeMainRepository
import com.bf.climbinglogbook.repositories.GradesRepository
import com.bf.climbinglogbook.repositories.MainRepository
import com.bf.climbinglogbook.ui.gradeCalc.GradeCalcViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddNewAscentViewModelTest{

    private lateinit var viewModel: AddNewAscentViewModel
    private lateinit var database: LogbookDatabase
    private lateinit var dao: AscentDAO

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LogbookDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.getAscentDao()
        viewModel = AddNewAscentViewModel(GradesRepository(), FakeMainRepository())
    }

    @After
    fun teardown(){
        database.close()
    }



}