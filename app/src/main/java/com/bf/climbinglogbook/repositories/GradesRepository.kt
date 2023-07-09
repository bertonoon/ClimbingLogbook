package com.bf.climbinglogbook.repositories

import com.bf.climbinglogbook.models.GradeSystem
import com.bf.climbinglogbook.models.gradeEnums.FrenchGrade
import com.bf.climbinglogbook.models.gradeEnums.KurtykaGrade
import com.bf.climbinglogbook.models.gradeEnums.UIAAGrade
import com.bf.climbinglogbook.models.gradeEnums.USAGrade
import dagger.Provides
import javax.inject.Singleton

class GradesRepository {

    fun getGradesMap() =
        mapOf(
            Pair(GradeSystem.FRENCH, FrenchGrade.getList()),
            Pair(GradeSystem.KURTYKA,KurtykaGrade.getList()),
            Pair(GradeSystem.USA,USAGrade.getList()),
            Pair(GradeSystem.UIAA,UIAAGrade.getList()),
        )
}