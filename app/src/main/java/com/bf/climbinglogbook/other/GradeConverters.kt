package com.bf.climbinglogbook.other

import com.bf.climbinglogbook.models.gradeEnums.KurtykaEnum
import com.bf.climbinglogbook.models.gradeEnums.USAEnum

class GradeConverters {

    fun frenchToKurtyka(){
        // TODO
    }

    fun frenchToUsa(){
        // TODO
    }

    fun kurtykaToFrench(){
        // TODO
    }

    fun kurtykaToUsa(kurtykaEnum: KurtykaEnum, hard: Boolean = false) : USAEnum?{
        val gradeNumber = KurtykaEnum.gradeToNumber(kurtykaEnum,hard)
        return USAEnum.numberToGrade(gradeNumber)
    }


    fun usaToKurtyka(usaEnum: USAEnum, hard: Boolean = false) : KurtykaEnum?{
        val gradeNumber = USAEnum.gradeToNumber(usaEnum)
        return KurtykaEnum.numberToGrade(gradeNumber,hard)
    }

    fun usaToFrench(){
        // TODO
    }


}