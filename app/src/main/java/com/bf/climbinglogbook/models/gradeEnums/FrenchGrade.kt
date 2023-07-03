package com.bf.climbinglogbook.models.gradeEnums

enum class FrenchGrade {
    ONE,
    TWO,
    TWO_PLUS,
    THREE,
    FOUR_A,
    FOUR_B,
    FOUR_C,
    FIVE_A,
    FIVE_B,
    FIVE_C,
    SIX_A,
    SIX_A_PLUS,
    SIX_B,
    SIX_B_PLUS,
    SIX_C,
    SIX_C_PLUS,
    SEVEN_A,
    SEVEN_A_PLUS,
    SEVEN_B,
    SEVEN_B_PLUS,
    SEVEN_C,
    SEVEN_C_PLUS,
    EIGHT_A,
    EIGHT_A_PLUS,
    EIGHT_B,
    EIGHT_B_PLUS,
    EIGHT_C,
    EIGHT_C_PLUS,
    NINE_A,
    NINE_A_PLUS,
    NINE_B,
    NINE_B_PLUS,
    NINE_C;

    override fun toString(): String {
        return when (this) {
            ONE -> "1"
            TWO -> "2"
            TWO_PLUS -> "2+"
            THREE -> "3"
            FOUR_A -> "4a"
            FOUR_B -> "4b"
            FOUR_C -> "4c"
            FIVE_A -> "5a"
            FIVE_B -> "5b"
            FIVE_C -> "5c"
            SIX_A -> "6a"
            SIX_A_PLUS -> "6a+"
            SIX_B -> "6b"
            SIX_B_PLUS -> "6b+"
            SIX_C -> "6c"
            SIX_C_PLUS -> "6c+"
            SEVEN_A -> "7a"
            SEVEN_A_PLUS -> "7a+"
            SEVEN_B -> "7b"
            SEVEN_B_PLUS -> "7b+"
            SEVEN_C -> "7c"
            SEVEN_C_PLUS -> "7c+"
            EIGHT_A -> "8a"
            EIGHT_A_PLUS -> "8a+"
            EIGHT_B -> "8b"
            EIGHT_B_PLUS -> "8b+"
            EIGHT_C -> "8c"
            EIGHT_C_PLUS -> "8c+"
            NINE_A -> "9a"
            NINE_A_PLUS -> "9a+"
            NINE_B -> "9b"
            NINE_B_PLUS -> "9b+"
            NINE_C -> "9c"
        }
    }

    companion object : GradeCompanionInterface<FrenchGrade> {
        override fun gradeToNumber(grade: FrenchGrade, hard: Boolean): Int {
            return when (grade) {
                ONE -> 2
                TWO -> 3
                TWO_PLUS -> 3
                THREE -> 4
                FOUR_A -> 5
                FOUR_B -> 5
                FOUR_C -> 6
                FIVE_A -> 6
                FIVE_B -> 7
                FIVE_C -> if (hard) 9 else 8
                SIX_A -> 10
                SIX_A_PLUS -> 11
                SIX_B -> 12
                SIX_B_PLUS -> 13
                SIX_C -> 14
                SIX_C_PLUS -> if (hard) 16 else 15
                SEVEN_A -> if (hard) 18 else 17
                SEVEN_A_PLUS -> 19
                SEVEN_B -> 20
                SEVEN_B_PLUS -> 21
                SEVEN_C -> 22
                SEVEN_C_PLUS -> 23
                EIGHT_A -> 24
                EIGHT_A_PLUS -> 25
                EIGHT_B -> 26
                EIGHT_B_PLUS -> 27
                EIGHT_C -> 28
                EIGHT_C_PLUS -> 29
                NINE_A -> 30
                NINE_A_PLUS -> 31
                NINE_B -> 32
                NINE_B_PLUS -> 33
                NINE_C -> 34
            }
        }

        override fun numberToGrade(number: Int, hard: Boolean): FrenchGrade? {
            return when (number) {
                1 -> ONE
                2 -> ONE
                3 -> if (hard) TWO_PLUS else TWO
                4 -> THREE
                5 -> if (hard) FOUR_B else FOUR_A
                6 -> if (hard) FIVE_A else FOUR_C
                7 -> FIVE_B
                8 -> FIVE_C
                9 -> FIVE_C
                10 -> SIX_A
                11 -> SIX_A_PLUS
                12 -> SIX_B
                13 -> SIX_B_PLUS
                14 -> SIX_C
                15 -> SIX_C_PLUS
                16 -> SIX_C_PLUS
                17 -> SEVEN_A
                18 -> SEVEN_A
                19 -> SEVEN_A_PLUS
                20 -> SEVEN_B
                21 -> SEVEN_B_PLUS
                22 -> SEVEN_C
                23 -> SEVEN_C_PLUS
                24 -> EIGHT_A
                25 -> EIGHT_A_PLUS
                26 -> EIGHT_B
                27 -> EIGHT_B_PLUS
                28 -> EIGHT_C
                29 -> EIGHT_C_PLUS
                30 -> NINE_A
                31 -> NINE_A_PLUS
                32 -> NINE_B
                33 -> NINE_B_PLUS
                34 -> NINE_C
                else -> null
            }
        }

        override fun getList(): List<String> {
            return FrenchGrade.values().map {
                it.toString()
            }
        }

    }

}



