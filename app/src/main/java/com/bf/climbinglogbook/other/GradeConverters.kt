package com.bf.climbinglogbook.other

import com.bf.climbinglogbook.models.gradeEnums.FrenchGrade
import com.bf.climbinglogbook.models.gradeEnums.KurtykaGrade
import com.bf.climbinglogbook.models.gradeEnums.USAGrade

class GradeConverters {

    fun frenchToKurtyka(frenchGrade: FrenchGrade, hard: Boolean = false): KurtykaGrade? {
        val gradeNumber = FrenchGrade.gradeToNumber(frenchGrade, hard)
        return KurtykaGrade.numberToGrade(gradeNumber, hard)
    }

    fun frenchToUsa(frenchGrade: FrenchGrade, hard: Boolean = false): USAGrade? {
        val gradeNumber = FrenchGrade.gradeToNumber(frenchGrade, hard)
        return USAGrade.numberToGrade(gradeNumber, hard)
    }

    fun kurtykaToFrench(kurtykaGrade: KurtykaGrade, hard: Boolean = false): FrenchGrade? {
        val gradeNumber = KurtykaGrade.gradeToNumber(kurtykaGrade, hard)
        return FrenchGrade.numberToGrade(gradeNumber, hard)
    }

    fun kurtykaToUsa(kurtykaGrade: KurtykaGrade, hard: Boolean = false): USAGrade? {
        val gradeNumber = KurtykaGrade.gradeToNumber(kurtykaGrade, hard)
        return USAGrade.numberToGrade(gradeNumber, hard)
    }

    fun usaToKurtyka(usaGrade: USAGrade, hard: Boolean = false): KurtykaGrade? {
        val gradeNumber = USAGrade.gradeToNumber(usaGrade, hard)
        return KurtykaGrade.numberToGrade(gradeNumber, hard)
    }

    fun usaToFrench(usaGrade: USAGrade, hard: Boolean = false): FrenchGrade? {
        val gradeNumber = USAGrade.gradeToNumber(usaGrade, hard)
        return FrenchGrade.numberToGrade(gradeNumber, hard)
    }


}