package com.bf.climbinglogbook.models.gradeEnums

enum class KurtykaGrade() {
    ONE,
    TWO,
    TWO_PLUS,
    THREE,
    THREE_PLUS,
    FOUR,
    FOUR_PLUS,
    FIVE,
    FIVE_PLUS,
    SIX_MINUS,
    SIX,
    SIX_PLUS,
    SIX_ONE,
    SIX_ONE_PLUS,
    SIX_TWO,
    SIX_TWO_PLUS,
    SIX_THREE,
    SIX_THREE_PLUS,
    SIX_FOUR,
    SIX_FOUR_PLUS,
    SIX_FIVE,
    SIX_FIVE_PLUS,
    SIX_SIX,
    SIX_SIX_PLUS,
    SIX_SEVEN,
    SIX_SEVEN_PLUS,
    SIX_EIGHT,
    SIX_EIGHT_PLUS,
    SIX_NINE,
    SIX_NINE_PLUS,
    SIX_TEN;

    override fun toString(): String {
        return when (this) {
            ONE -> "I"
            TWO -> "II"
            TWO_PLUS -> "II+"
            THREE -> "III"
            THREE_PLUS -> "III+"
            FOUR -> "IV"
            FOUR_PLUS -> "IV+"
            FIVE -> "V"
            FIVE_PLUS -> "V+"
            SIX_MINUS -> "VI-"
            SIX -> "VI"
            SIX_PLUS -> "VI+"
            SIX_ONE -> "VI.1"
            SIX_ONE_PLUS -> "VI.1+"
            SIX_TWO -> "VI.2"
            SIX_TWO_PLUS -> "VI.2+"
            SIX_THREE -> "VI.3"
            SIX_THREE_PLUS -> "VI.3+"
            SIX_FOUR -> "VI.4"
            SIX_FOUR_PLUS -> "VI.4+"
            SIX_FIVE -> "VI.5"
            SIX_FIVE_PLUS -> "VI.5+"
            SIX_SIX -> "VI.6"
            SIX_SIX_PLUS -> "VI.6+"
            SIX_SEVEN -> "VI.7"
            SIX_SEVEN_PLUS -> "VI.7+"
            SIX_EIGHT -> "VI.8"
            SIX_EIGHT_PLUS -> "VI.8+"
            SIX_NINE -> "VI.9"
            SIX_NINE_PLUS -> "VI.9+"
            SIX_TEN -> "VI.10"
        }
    }

    companion object : GradeCompanionInterface<KurtykaGrade> {
        override fun gradeToNumber(grade: KurtykaGrade, hard: Boolean): Int {
            return when (grade) {
                ONE -> 2
                TWO -> 3
                TWO_PLUS -> 3
                THREE -> 4
                THREE_PLUS -> 4
                FOUR -> 5
                FOUR_PLUS -> 6
                FIVE -> 7
                FIVE_PLUS -> 8
                SIX_MINUS -> 9
                SIX -> 10
                SIX_PLUS -> 11
                SIX_ONE -> 12
                SIX_ONE_PLUS -> 13
                SIX_TWO -> 14
                SIX_TWO_PLUS -> if (hard) 16 else 15
                SIX_THREE -> if (hard) 18 else 17
                SIX_THREE_PLUS -> 19
                SIX_FOUR -> 20
                SIX_FOUR_PLUS -> if (hard) 22 else 21
                SIX_FIVE -> 23
                SIX_FIVE_PLUS -> 24
                SIX_SIX -> 25
                SIX_SIX_PLUS -> if (hard) 27 else 26
                SIX_SEVEN -> 28
                SIX_SEVEN_PLUS -> 29
                SIX_EIGHT -> 30
                SIX_EIGHT_PLUS -> 31
                SIX_NINE -> 32
                SIX_NINE_PLUS -> 33
                SIX_TEN -> 34
            }
        }

        override fun numberToGrade(number: Int, hard: Boolean): KurtykaGrade? {
            return when (number) {
                1 -> ONE
                2 -> ONE
                3 -> if (hard) TWO_PLUS else TWO
                4 -> if (hard) THREE_PLUS else THREE
                5 -> FOUR
                6 -> FIVE
                7 -> FIVE
                8 -> FIVE_PLUS
                9 -> SIX_MINUS
                10 -> SIX
                11 -> SIX_PLUS
                12 -> SIX_ONE
                13 -> SIX_ONE_PLUS
                14 -> SIX_TWO
                15 -> SIX_TWO_PLUS
                16 -> SIX_TWO_PLUS
                17 -> SIX_THREE
                18 -> SIX_THREE
                19 -> SIX_THREE_PLUS
                20 -> SIX_FOUR
                21 -> SIX_FOUR_PLUS
                22 -> SIX_FOUR_PLUS
                23 -> SIX_FIVE
                24 -> SIX_FIVE_PLUS
                25 -> SIX_SIX
                26 -> SIX_SIX_PLUS
                27 -> SIX_SIX_PLUS
                28 -> SIX_SEVEN
                29 -> SIX_SEVEN_PLUS
                30 -> SIX_EIGHT
                31 -> SIX_EIGHT_PLUS
                32 -> SIX_NINE
                33 -> SIX_NINE_PLUS
                34 -> SIX_TEN
                else -> null
            }
        }

        override fun getList(): List<String> {
            return values().map {
                it.toString()
            }
        }

    }

}
