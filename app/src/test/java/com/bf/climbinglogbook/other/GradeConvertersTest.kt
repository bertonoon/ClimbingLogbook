package com.bf.climbinglogbook.other

import com.bf.climbinglogbook.models.gradeEnums.KurtykaEnum
import com.bf.climbinglogbook.models.gradeEnums.USAEnum
import org.junit.Assert.*

import org.junit.Test

class GradeConvertersTest {

    @Test
    fun kurtykaToUsa_correctValueWithHardParamTrue() {
        val kurtykaGrade = KurtykaEnum.SIX_THREE
        val usaGrade = GradeConverters().kurtykaToUsa(kurtykaGrade, true)
        assertEquals(USAEnum.FIVE_ELEVEN_D, usaGrade)
    }

    @Test
    fun kurtykaToUsa_correctValueWithHardParamFalse() {
        val kurtykaGrade = KurtykaEnum.SIX_THREE
        val usaGrade = GradeConverters().kurtykaToUsa(kurtykaGrade, false)
        assertEquals(USAEnum.FIVE_ELEVEN_C, usaGrade)
    }

    @Test
    fun kurtykaToUsa_correctValueWithoutHardParam() {
        val kurtykaGrade = KurtykaEnum.SIX_TWO_PLUS
        val usaGrade = GradeConverters().kurtykaToUsa(kurtykaGrade)
        assertEquals(USAEnum.FIVE_ELEVEN_A, usaGrade)
    }

    @Test
    fun kurtykaToUsa_maxCorrectValueWithoutHardParam() {
        val kurtykaGrade: KurtykaEnum = KurtykaEnum.SIX_TEN
        val usaGrade = GradeConverters().kurtykaToUsa(kurtykaGrade)
        assertEquals(USAEnum.FIVE_FIFTEEN_D, usaGrade)
    }

    @Test
    fun kurtykaToUsa_minCorrectValueWithoutHardParam() {
        val kurtykaGrade: KurtykaEnum = KurtykaEnum.ONE
        val usaGrade = GradeConverters().kurtykaToUsa(kurtykaGrade)
        assertEquals(USAEnum.FIVE_ONE, usaGrade)
    }

    @Test
    fun usaToKurtyka_correctValueWithHardParamTrue() {
        val usaGrade = USAEnum.FIVE_TWO
        val kurtykaGrade = GradeConverters().usaToKurtyka(usaGrade, true)
        assertEquals(KurtykaEnum.TWO_PLUS, kurtykaGrade)
    }

    @Test
    fun usaToKurtyka_correctValueWithHardParamFalse() {
        val usaGrade = USAEnum.FIVE_TWO
        val kurtykaGrade = GradeConverters().usaToKurtyka(usaGrade, false)
        assertEquals(KurtykaEnum.TWO, kurtykaGrade)
    }

    @Test
    fun usaToKurtyka_correctDiffValuesWithoutParamsAndSameKurtykaGrade() {
        val kurtykaGrade = arrayOf(
            GradeConverters().usaToKurtyka(USAEnum.FIVE_TWELVE_C),
            GradeConverters().usaToKurtyka(USAEnum.FIVE_TWELVE_D)
        )
        assertArrayEquals(
            arrayOf(KurtykaEnum.SIX_FOUR_PLUS, KurtykaEnum.SIX_FOUR_PLUS),
            kurtykaGrade
        )
    }

    @Test
    fun usaToKurtyka_maxCorrectValueWithoutHardParam() {
        val usaGrade = USAEnum.FIVE_FIFTEEN_D
        val kurtykaGrade = GradeConverters().usaToKurtyka(usaGrade)
        assertEquals(KurtykaEnum.SIX_TEN, kurtykaGrade)
    }

    @Test
    fun usaToKurtyka_minCorrectValueWithoutHardParam() {
        val usaGrade = USAEnum.FIVE_ZERO
        val kurtykaGrade = GradeConverters().usaToKurtyka(usaGrade)
        assertEquals(KurtykaEnum.ONE, kurtykaGrade)
    }


}