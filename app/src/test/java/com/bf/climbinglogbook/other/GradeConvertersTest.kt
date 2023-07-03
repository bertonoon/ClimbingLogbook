package com.bf.climbinglogbook.other

import com.bf.climbinglogbook.models.gradeEnums.FrenchGrade
import com.bf.climbinglogbook.models.gradeEnums.KurtykaGrade
import com.bf.climbinglogbook.models.gradeEnums.USAGrade
import org.junit.Assert.*

import org.junit.Test

class GradeConvertersTest {

    @Test
    fun kurtykaToUsa_correctValueWithHardParamTrue() {
        val kurtykaGrade = KurtykaGrade.SIX_THREE
        val usaGrade = GradeConverters().kurtykaToUsa(kurtykaGrade, true)
        assertEquals(USAGrade.FIVE_ELEVEN_D, usaGrade)
    }

    @Test
    fun kurtykaToUsa_correctValueWithHardParamFalse() {
        val kurtykaGrade = KurtykaGrade.SIX_THREE
        val usaGrade = GradeConverters().kurtykaToUsa(kurtykaGrade, false)
        assertEquals(USAGrade.FIVE_ELEVEN_C, usaGrade)
    }

    @Test
    fun kurtykaToUsa_correctValueWithoutHardParam() {
        val kurtykaGrade = KurtykaGrade.SIX_TWO_PLUS
        val usaGrade = GradeConverters().kurtykaToUsa(kurtykaGrade)
        assertEquals(USAGrade.FIVE_ELEVEN_A, usaGrade)
    }

    @Test
    fun kurtykaToUsa_maxCorrectValueWithoutHardParam() {
        val kurtykaGrade: KurtykaGrade = KurtykaGrade.SIX_TEN
        val usaGrade = GradeConverters().kurtykaToUsa(kurtykaGrade)
        assertEquals(USAGrade.FIVE_FIFTEEN_D, usaGrade)
    }

    @Test
    fun kurtykaToUsa_minCorrectValueWithoutHardParam() {
        val kurtykaGrade: KurtykaGrade = KurtykaGrade.ONE
        val usaGrade = GradeConverters().kurtykaToUsa(kurtykaGrade)
        assertEquals(USAGrade.FIVE_ONE, usaGrade)
    }
    @Test
    fun kurtykaToFrench_correctValueWithHardParamTrue() {
        val kurtykaGrade = KurtykaGrade.FOUR_PLUS
        val frenchGrade = GradeConverters().kurtykaToFrench(kurtykaGrade, true)
        assertEquals(FrenchGrade.FIVE_A, frenchGrade)
    }

    @Test
    fun kurtykaToFrench_correctValueWithHardParamFalse() {
        val kurtykaGrade = KurtykaGrade.FOUR_PLUS
        val frenchGrade = GradeConverters().kurtykaToFrench(kurtykaGrade, false)
        assertEquals(FrenchGrade.FOUR_C, frenchGrade)
    }

    @Test
    fun kurtykaToFrench_correctValueWithoutHardParam() {
        val kurtykaGrade = KurtykaGrade.FOUR
        val frenchGrade = GradeConverters().kurtykaToFrench(kurtykaGrade)
        assertEquals(FrenchGrade.FOUR_A, frenchGrade)
    }

    @Test
    fun kurtykaToFrench_maxCorrectValueWithoutHardParam() {
        val kurtykaGrade: KurtykaGrade = KurtykaGrade.SIX_TEN
        val frenchGrade = GradeConverters().kurtykaToFrench(kurtykaGrade)
        assertEquals(FrenchGrade.NINE_C, frenchGrade)
    }

    @Test
    fun kurtykaToFrench_minCorrectValueWithoutHardParam() {
        val kurtykaGrade: KurtykaGrade = KurtykaGrade.ONE
        val frenchGrade = GradeConverters().kurtykaToFrench(kurtykaGrade)
        assertEquals(FrenchGrade.ONE, frenchGrade)
    }

    @Test
    fun usaToKurtyka_correctValueWithHardParamTrue() {
        val usaGrade = USAGrade.FIVE_TWO
        val kurtykaGrade = GradeConverters().usaToKurtyka(usaGrade, true)
        assertEquals(KurtykaGrade.TWO_PLUS, kurtykaGrade)
    }

    @Test
    fun usaToKurtyka_correctValueWithHardParamFalse() {
        val usaGrade = USAGrade.FIVE_TWO
        val kurtykaGrade = GradeConverters().usaToKurtyka(usaGrade, false)
        assertEquals(KurtykaGrade.TWO, kurtykaGrade)
    }

    @Test
    fun usaToKurtyka_correctDiffValuesWithoutParamsAndSameKurtykaGrade() {
        val kurtykaGrade = arrayOf(
            GradeConverters().usaToKurtyka(USAGrade.FIVE_TWELVE_C),
            GradeConverters().usaToKurtyka(USAGrade.FIVE_TWELVE_D)
        )
        assertArrayEquals(
            arrayOf(KurtykaGrade.SIX_FOUR_PLUS, KurtykaGrade.SIX_FOUR_PLUS),
            kurtykaGrade
        )
    }

    @Test
    fun usaToKurtyka_maxCorrectValueWithoutHardParam() {
        val usaGrade = USAGrade.FIVE_FIFTEEN_D
        val kurtykaGrade = GradeConverters().usaToKurtyka(usaGrade)
        assertEquals(KurtykaGrade.SIX_TEN, kurtykaGrade)
    }

    @Test
    fun usaToKurtyka_minCorrectValueWithoutHardParam() {
        val usaGrade = USAGrade.FIVE_ZERO
        val kurtykaGrade = GradeConverters().usaToKurtyka(usaGrade)
        assertEquals(KurtykaGrade.ONE, kurtykaGrade)
    }
    @Test
    fun usaToFrench_correctValueWithHardParamTrue() {
        val usaGrade = USAGrade.FIVE_TWO
        val frenchGrade = GradeConverters().usaToFrench(usaGrade, true)
        assertEquals(FrenchGrade.TWO_PLUS, frenchGrade)
    }

    @Test
    fun usaToFrench_correctValueWithHardParamFalse() {
        val usaGrade = USAGrade.FIVE_TWO
        val frenchGrade = GradeConverters().usaToFrench(usaGrade, false)
        assertEquals(FrenchGrade.TWO, frenchGrade)
    }

    @Test
    fun usaToFrench_correctDiffValuesWithoutParamsAndSameFrenchGrade() {
        val frenchGrades = arrayOf(
            GradeConverters().usaToFrench(USAGrade.FIVE_ELEVEN_C),
            GradeConverters().usaToFrench(USAGrade.FIVE_ELEVEN_D)
        )
        assertArrayEquals(
            arrayOf(FrenchGrade.SEVEN_A, FrenchGrade.SEVEN_A),
            frenchGrades
        )
    }

    @Test
    fun usaToFrench_maxCorrectValueWithoutHardParam() {
        val usaGrade = USAGrade.FIVE_FIFTEEN_D
        val frenchGrade = GradeConverters().usaToFrench(usaGrade)
        assertEquals(FrenchGrade.NINE_C, frenchGrade)
    }

    @Test
    fun usaToFrench_minCorrectValueWithoutHardParam() {
        val usaGrade = USAGrade.FIVE_ZERO
        val frenchGrade = GradeConverters().usaToFrench(usaGrade)
        assertEquals(FrenchGrade.ONE, frenchGrade)
    }
    @Test
    fun frenchToUsa_correctValueWithHardParamTrue() {
        val frenchGrade = FrenchGrade.FIVE_C
        val usaGrade = GradeConverters().frenchToUsa(frenchGrade, true)
        assertEquals(USAGrade.FIVE_EIGHT, usaGrade)
    }

    @Test
    fun frenchToUsa_correctValueWithHardParamFalse() {
        val frenchGrade = FrenchGrade.FIVE_C
        val usaGrade = GradeConverters().frenchToUsa(frenchGrade, false)
        assertEquals(USAGrade.FIVE_SEVEN, usaGrade)
    }

    @Test
    fun frenchToUsa_correctValueWithoutHardParam() {
        val frenchGrade = FrenchGrade.SEVEN_A
        val usaGrade = GradeConverters().frenchToUsa(frenchGrade)
        assertEquals(USAGrade.FIVE_ELEVEN_C, usaGrade)
    }

    @Test
    fun frenchToUsa_maxCorrectValueWithoutHardParam() {
        val frenchGrade = FrenchGrade.NINE_C
        val usaGrade = GradeConverters().frenchToUsa(frenchGrade)
        assertEquals(USAGrade.FIVE_FIFTEEN_D, usaGrade)
    }

    @Test
    fun frenchToUsa_minCorrectValueWithoutHardParam() {
        val frenchGrade = FrenchGrade.ONE
        val usaGrade = GradeConverters().frenchToUsa(frenchGrade)
        assertEquals(USAGrade.FIVE_ONE, usaGrade)
    }

    @Test
    fun frenchToKurtyka_correctValueWithHardParamTrue() {
        val frenchGrade = FrenchGrade.FIVE_C
        val kurtykaGrade = GradeConverters().frenchToKurtyka(frenchGrade, true)
        assertEquals(KurtykaGrade.SIX_MINUS, kurtykaGrade)
    }

    @Test
    fun frenchToKurtyka_correctValueWithHardParamFalse() {
        val frenchGrade = FrenchGrade.FIVE_C
        val kurtykaGrade = GradeConverters().frenchToKurtyka(frenchGrade, false)
        assertEquals(KurtykaGrade.FIVE_PLUS, kurtykaGrade)
    }

    @Test
    fun frenchToKurtyka_correctValueWithoutHardParam() {
        val frenchGrade = FrenchGrade.THREE
        val kurtykaGrade = GradeConverters().frenchToKurtyka(frenchGrade)
        assertEquals(KurtykaGrade.THREE, kurtykaGrade)
    }

    @Test
    fun frenchToKurtyka_maxCorrectValueWithoutHardParam() {
        val frenchGrade = FrenchGrade.NINE_C
        val kurtykaGrade = GradeConverters().frenchToKurtyka(frenchGrade)
        assertEquals(KurtykaGrade.SIX_TEN, kurtykaGrade)
    }

    @Test
    fun frenchToKurtyka_minCorrectValueWithoutHardParam() {
        val frenchGrade = FrenchGrade.ONE
        val kurtykaGrade = GradeConverters().frenchToKurtyka(frenchGrade)
        assertEquals(KurtykaGrade.ONE, kurtykaGrade)
    }


}