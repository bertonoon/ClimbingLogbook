package com.bf.climbinglogbook.other

import com.bf.climbinglogbook.models.gradeEnums.FrenchGrade
import com.bf.climbinglogbook.models.gradeEnums.KurtykaGrade
import com.bf.climbinglogbook.models.gradeEnums.UIAAGrade
import com.bf.climbinglogbook.models.gradeEnums.USAGrade

class GradeConverters {

    fun frenchToKurtyka(frenchGrade: FrenchGrade, hard: Boolean = false): KurtykaGrade? {
        if (frenchGrade == FrenchGrade.TWO) return KurtykaGrade.TWO
        if (frenchGrade == FrenchGrade.TWO_PLUS) return KurtykaGrade.TWO_PLUS

        val gradeNumber = FrenchGrade.gradeToNumber(frenchGrade, hard)
        return KurtykaGrade.numberToGrade(gradeNumber, hard)
    }

    fun frenchToUsa(frenchGrade: FrenchGrade, hard: Boolean = false): USAGrade? {
        val gradeNumber = FrenchGrade.gradeToNumber(frenchGrade, hard)
        return USAGrade.numberToGrade(gradeNumber, hard)
    }

    fun frenchToUiaa(frenchGrade: FrenchGrade, hard: Boolean = false): UIAAGrade? {
        if (frenchGrade == FrenchGrade.FOUR_C) return UIAAGrade.FOUR_PLUS
        if (frenchGrade == FrenchGrade.FIVE_A) return UIAAGrade.FIVE_MINUS
        if (frenchGrade == FrenchGrade.TWO) return UIAAGrade.TWO
        if (frenchGrade == FrenchGrade.TWO_PLUS) return UIAAGrade.TWO_PLUS

        val gradeNumber = FrenchGrade.gradeToNumber(frenchGrade, hard)
        return UIAAGrade.numberToGrade(gradeNumber, hard)
    }

    fun kurtykaToFrench(kurtykaGrade: KurtykaGrade, hard: Boolean = false): FrenchGrade? {
        if (kurtykaGrade == KurtykaGrade.TWO) return FrenchGrade.TWO
        if (kurtykaGrade == KurtykaGrade.TWO_PLUS) return FrenchGrade.TWO_PLUS

        val gradeNumber = KurtykaGrade.gradeToNumber(kurtykaGrade, hard)
        return FrenchGrade.numberToGrade(gradeNumber, hard)
    }

    fun kurtykaToUsa(kurtykaGrade: KurtykaGrade, hard: Boolean = false): USAGrade? {
        val gradeNumber = KurtykaGrade.gradeToNumber(kurtykaGrade, hard)
        return USAGrade.numberToGrade(gradeNumber, hard)
    }

    fun kurtykaToUiaa(kurtykaGrade: KurtykaGrade, hard: Boolean = false): UIAAGrade? {
        if (kurtykaGrade == KurtykaGrade.TWO) return UIAAGrade.TWO
        if (kurtykaGrade == KurtykaGrade.TWO_PLUS) return UIAAGrade.TWO_PLUS
        if (kurtykaGrade == KurtykaGrade.THREE) return UIAAGrade.THREE
        if (kurtykaGrade == KurtykaGrade.THREE_PLUS) return UIAAGrade.THREE_PLUS

        val gradeNumber = KurtykaGrade.gradeToNumber(kurtykaGrade, hard)
        return UIAAGrade.numberToGrade(gradeNumber, hard)
    }

    fun usaToKurtyka(usaGrade: USAGrade, hard: Boolean = false): KurtykaGrade? {
        val gradeNumber = USAGrade.gradeToNumber(usaGrade, hard)
        return KurtykaGrade.numberToGrade(gradeNumber, hard)
    }

    fun usaToFrench(usaGrade: USAGrade, hard: Boolean = false): FrenchGrade? {
        val gradeNumber = USAGrade.gradeToNumber(usaGrade, hard)
        return FrenchGrade.numberToGrade(gradeNumber, hard)
    }

    fun usaToUiaa(usaGrade: USAGrade, hard: Boolean = false): UIAAGrade? {
        val gradeNumber = USAGrade.gradeToNumber(usaGrade, hard)
        return UIAAGrade.numberToGrade(gradeNumber, hard)
    }

    fun uiaaToKurtyka(uiaaGrade: UIAAGrade, hard: Boolean = false): KurtykaGrade? {
        if (uiaaGrade == UIAAGrade.TWO) return KurtykaGrade.TWO
        if (uiaaGrade == UIAAGrade.TWO_PLUS) return KurtykaGrade.TWO_PLUS
        if (uiaaGrade == UIAAGrade.THREE) return KurtykaGrade.THREE
        if (uiaaGrade == UIAAGrade.THREE_PLUS) return KurtykaGrade.THREE_PLUS

        val gradeNumber = UIAAGrade.gradeToNumber(uiaaGrade, hard)
        return KurtykaGrade.numberToGrade(gradeNumber, hard)
    }

    fun uiaaToFrench(uiaaGrade: UIAAGrade, hard: Boolean = false): FrenchGrade? {
        if (uiaaGrade == UIAAGrade.FOUR_PLUS) return FrenchGrade.FOUR_C
        if (uiaaGrade == UIAAGrade.FIVE_MINUS) return FrenchGrade.FIVE_A
        if (uiaaGrade == UIAAGrade.TWO) return FrenchGrade.TWO
        if (uiaaGrade == UIAAGrade.TWO_PLUS) return FrenchGrade.TWO_PLUS

        val gradeNumber = UIAAGrade.gradeToNumber(uiaaGrade, hard)
        return FrenchGrade.numberToGrade(gradeNumber, hard)
    }

    fun uiaaToUsa(uiaaGrade: UIAAGrade, hard: Boolean = false): USAGrade? {
        val gradeNumber = UIAAGrade.gradeToNumber(uiaaGrade, hard)
        return USAGrade.numberToGrade(gradeNumber, hard)
    }


}