package com.bf.climbinglogbook.models.gradeEnums

enum class UIAAGrade {
    ONE,
    TWO,
    TWO_PLUS,
    THREE,
    THREE_PLUS,
    FOUR,
    FOUR_PLUS,
    FIVE_MINUS,
    FIVE,
    FIVE_PLUS,
    SIX_MINUS,
    SIX,
    SIX_PLUS,
    SEVEN_MINUS,
    SEVEN,
    SEVEN_PLUS,
    EIGHT_MINUS,
    EIGHT,
    EIGHT_PLUS,
    NINE_MINUS,
    NINE,
    NINE_PLUS,
    TEN_MINUS,
    TEN,
    TEN_PLUS,
    ELEVEN_MINUS,
    ELEVEN,
    ELEVEN_PLUS,
    TWELVE_MINUS,
    TWELVE,
    TWELVE_PLUS;

    override fun toString(): String {
        return when (this) {
            ONE -> "I"
            TWO -> "II"
            TWO_PLUS -> "II+"
            THREE -> "III"
            THREE_PLUS -> "III+"
            FOUR -> "IV"
            FOUR_PLUS -> "IV+"
            FIVE_MINUS -> "V-"
            FIVE -> "V"
            FIVE_PLUS -> "V+"
            SIX_MINUS -> "VI-"
            SIX -> "VI"
            SIX_PLUS -> "VI+"
            SEVEN_MINUS -> "VII-"
            SEVEN -> "VII"
            SEVEN_PLUS -> "VII+"
            EIGHT_MINUS -> "VIII-"
            EIGHT -> "VIII"
            EIGHT_PLUS -> "VIII+"
            NINE_MINUS -> "IX-"
            NINE -> "IX"
            NINE_PLUS -> "IX+"
            TEN_MINUS -> "X-"
            TEN -> "X"
            TEN_PLUS -> "X+"
            ELEVEN_MINUS -> "XI-"
            ELEVEN -> "XI"
            ELEVEN_PLUS -> "XI+"
            TWELVE_MINUS -> "XII-"
            TWELVE -> "XII"
            TWELVE_PLUS -> "XII+"
        }
    }

    companion object : GradeCompanionInterface<UIAAGrade> {
        override fun gradeToNumber(grade: UIAAGrade, hard: Boolean): Int {
            return when (grade) {
                ONE -> 2
                TWO -> 3
                TWO_PLUS -> 3
                THREE -> 4
                THREE_PLUS -> 4
                FOUR -> 5
                FOUR_PLUS -> 6
                FIVE_MINUS -> 6
                FIVE -> 7
                FIVE_PLUS -> 8
                SIX_MINUS -> 9
                SIX -> 10
                SIX_PLUS -> 11
                SEVEN_MINUS -> 12
                SEVEN -> 13
                SEVEN_PLUS -> 14
                EIGHT_MINUS -> if (hard) 16 else 15
                EIGHT -> if (hard) 18 else 17
                EIGHT_PLUS -> 19
                NINE_MINUS -> 20
                NINE -> if (hard) 22 else 21
                NINE_PLUS -> 23
                TEN_MINUS -> if (hard) 25 else 24
                TEN -> 26
                TEN_PLUS -> 27
                ELEVEN_MINUS -> if (hard) 29 else 28
                ELEVEN -> 30
                ELEVEN_PLUS -> 31
                TWELVE_MINUS -> 32
                TWELVE -> 33
                TWELVE_PLUS -> 34
            }
        }

        override fun numberToGrade(number: Int, hard: Boolean): UIAAGrade? {
            return when (number) {
                1 -> ONE
                2 -> ONE
                3 -> if (hard) TWO_PLUS else TWO
                4 -> if (hard) THREE_PLUS else THREE
                5 -> FOUR
                6 -> if (hard) FIVE_MINUS else FOUR_PLUS
                7 -> FIVE
                8 -> FIVE_PLUS
                9 -> SIX_MINUS
                10 -> SIX
                11 -> SIX_PLUS
                12 -> SEVEN_MINUS
                13 -> SEVEN
                14 -> SEVEN_PLUS
                15 -> EIGHT_MINUS
                16 -> EIGHT_MINUS
                17 -> EIGHT
                18 -> EIGHT
                19 -> EIGHT_PLUS
                20 -> NINE_MINUS
                21 -> NINE
                22 -> NINE
                23 -> NINE_PLUS
                24 -> TEN_MINUS
                25 -> TEN_MINUS
                26 -> TEN
                27 -> TEN_PLUS
                28 -> ELEVEN_MINUS
                29 -> ELEVEN_MINUS
                30 -> ELEVEN
                31 -> ELEVEN_PLUS
                32 -> TWELVE_MINUS
                33 -> TWELVE
                34 -> TWELVE_PLUS
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