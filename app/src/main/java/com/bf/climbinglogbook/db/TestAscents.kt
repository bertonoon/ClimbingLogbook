package com.bf.climbinglogbook.db

import com.bf.climbinglogbook.models.AscentStyle
import com.bf.climbinglogbook.models.BelayType
import com.bf.climbinglogbook.models.ClimbingType
import com.bf.climbinglogbook.models.GradeSystem
import java.util.Date


class TestAscents {

    companion object {
        fun getList() = listOf(
            Ascent(
                name = "Znikający Punk",
                date = Date(),
                originalGradeSystem = GradeSystem.KURTYKA,
                originalGradeOrdinal = 16,
                ascentStyle = AscentStyle.FLASH,
                climbingType = ClimbingType.SPORT,
                usaGradeNumber = 16,
                belayType = BelayType.LEAD,
                meters = 9
            ),
            Ascent(
                name = "Krzyż",
                date = Date(),
                originalGradeSystem = GradeSystem.KURTYKA,
                originalGradeOrdinal = 12,
                ascentStyle = AscentStyle.REDPOINT,
                climbingType = ClimbingType.TRAD,
                usaGradeNumber = 11,
                belayType = BelayType.LEAD,
                meters = 3
            ),
            Ascent(
                name = "Coś trudnego",
                date = Date(),
                originalGradeSystem = GradeSystem.KURTYKA,
                originalGradeOrdinal = 21,
                ascentStyle = AscentStyle.REDPOINT,
                climbingType = ClimbingType.SPORT,
                usaGradeNumber = 21,
                belayType = BelayType.LEAD,
                meters = 21
            ),
            Ascent(
                name = "Łatwe",
                date = Date(),
                originalGradeSystem = GradeSystem.KURTYKA,
                originalGradeOrdinal = 3,
                ascentStyle = AscentStyle.ON_SIGHT,
                climbingType = ClimbingType.SPORT,
                usaGradeNumber = 3,
                belayType = BelayType.LEAD,
                meters = 5
            ),
            Ascent(
                name = "Bez nazwy",
                date = Date(),
                originalGradeSystem = GradeSystem.KURTYKA,
                originalGradeOrdinal = 10,
                ascentStyle = AscentStyle.FLASH,
                climbingType = ClimbingType.TRAD,
                usaGradeNumber = 10,
                belayType = BelayType.LEAD,
                meters = 10
            ),
            Ascent(
                name = "Kolejne cudo",
                date = Date(),
                originalGradeSystem = GradeSystem.KURTYKA,
                originalGradeOrdinal = 14,
                ascentStyle = AscentStyle.REDPOINT,
                climbingType = ClimbingType.SPORT,
                usaGradeNumber = 14,
                belayType = BelayType.LEAD,
                meters = 13
            ),

        )

    }
}