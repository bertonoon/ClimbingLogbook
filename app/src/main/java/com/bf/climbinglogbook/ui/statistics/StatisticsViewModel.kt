package com.bf.climbinglogbook.ui.statistics

import android.util.Log
import androidx.lifecycle.ViewModel
import com.bf.climbinglogbook.db.Ascent
import com.bf.climbinglogbook.models.GradeSystem
import com.bf.climbinglogbook.models.PieChartGrade
import com.bf.climbinglogbook.models.gradeEnums.FrenchGrade
import com.bf.climbinglogbook.models.gradeEnums.USAGrade
import com.bf.climbinglogbook.other.GradeConverters
import com.bf.climbinglogbook.repositories.MainRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val repository: MainRepositoryInterface
) : ViewModel() {

    val ascentsSortedByGradeAsc = repository.getAllAscentsSortedByGradeAsc()

    fun calculateAllAscentsToFrench(ascents: List<Ascent>): List<PieChartGrade> {
        if (ascents.isEmpty()) return listOf()

        val ascentsCount = mutableListOf<PieChartGrade>()
        for (grade in FrenchGrade.getList()) {
            ascentsCount.add(PieChartGrade(grade, 0))
        }

        for (ascent in ascents) {
            if (ascent.originalGradeSystem == GradeSystem.FRENCH) {
                ascentsCount[ascent.originalGradeOrdinal].gradeCount++
            } else {
                val usaGrade = USAGrade.numberToGrade(ascent.usaGradeNumber, ascent.hard) ?: break
                val frenchGrade = GradeConverters().usaToFrench(usaGrade, ascent.hard) ?: break
                val frenchGradeOrdinal = FrenchGrade.gradeToNumber(frenchGrade, ascent.hard)
                ascentsCount[frenchGradeOrdinal-1].gradeCount++
            }
        }
        return ascentsCount
    }
}