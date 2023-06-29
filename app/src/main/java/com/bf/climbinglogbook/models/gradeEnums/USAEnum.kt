package com.bf.climbinglogbook.models.gradeEnums


enum class USAEnum {
    FIVE_ZERO,
    FIVE_ONE,
    FIVE_TWO,
    FIVE_THREE,
    FIVE_FOUR,
    FIVE_FIVE,
    FIVE_SIX,
    FIVE_SEVEN,
    FIVE_EIGHT,
    FIVE_NINE,
    FIVE_TEN_A,
    FIVE_TEN_B,
    FIVE_TEN_C,
    FIVE_TEN_D,
    FIVE_ELEVEN_A,
    FIVE_ELEVEN_B,
    FIVE_ELEVEN_C,
    FIVE_ELEVEN_D,
    FIVE_TWELVE_A,
    FIVE_TWELVE_B,
    FIVE_TWELVE_C,
    FIVE_TWELVE_D,
    FIVE_THIRTEEN_A,
    FIVE_THIRTEEN_B,
    FIVE_THIRTEEN_C,
    FIVE_THIRTEEN_D,
    FIVE_FOURTEEN_A,
    FIVE_FOURTEEN_B,
    FIVE_FOURTEEN_C,
    FIVE_FOURTEEN_D,
    FIVE_FIFTEEN_A,
    FIVE_FIFTEEN_B,
    FIVE_FIFTEEN_C,
    FIVE_FIFTEEN_D;

    override fun toString(): String {
        return when(this){
            FIVE_ZERO -> "5.0"
            FIVE_ONE -> "5.1"
            FIVE_TWO -> "5.2"
            FIVE_THREE -> "5.3"
            FIVE_FOUR -> "5.4"
            FIVE_FIVE -> "5.5"
            FIVE_SIX -> "5.6"
            FIVE_SEVEN -> "5.7"
            FIVE_EIGHT -> "5.8"
            FIVE_NINE -> "5.9"
            FIVE_TEN_A -> "5.10a"
            FIVE_TEN_B -> "5.10b"
            FIVE_TEN_C -> "5.10c"
            FIVE_TEN_D -> "5.10d"
            FIVE_ELEVEN_A -> "5.11a"
            FIVE_ELEVEN_B -> "5.11b"
            FIVE_ELEVEN_C -> "5.11c"
            FIVE_ELEVEN_D -> "5.11d"
            FIVE_TWELVE_A -> "5.12a"
            FIVE_TWELVE_B -> "5.12b"
            FIVE_TWELVE_C -> "5.12c"
            FIVE_TWELVE_D -> "5.12d"
            FIVE_THIRTEEN_A -> "5.13a"
            FIVE_THIRTEEN_B -> "5.13b"
            FIVE_THIRTEEN_C -> "5.13c"
            FIVE_THIRTEEN_D -> "5.13d"
            FIVE_FOURTEEN_A -> "5.14a"
            FIVE_FOURTEEN_B -> "5.14b"
            FIVE_FOURTEEN_C -> "5.14c"
            FIVE_FOURTEEN_D -> "5.14d"
            FIVE_FIFTEEN_A -> "5.15a"
            FIVE_FIFTEEN_B -> "5.15b"
            FIVE_FIFTEEN_C -> "5.15c"
            FIVE_FIFTEEN_D -> "5.15d"
        }
    }

    companion object {
        fun gradeToNumber(grade: USAEnum): Int {
            return when (grade) {
                FIVE_ZERO -> 1
                FIVE_ONE -> 2
                FIVE_TWO -> 3
                FIVE_THREE -> 4
                FIVE_FOUR -> 5
                FIVE_FIVE -> 6
                FIVE_SIX -> 7
                FIVE_SEVEN -> 8
                FIVE_EIGHT -> 9
                FIVE_NINE -> 10
                FIVE_TEN_A -> 11
                FIVE_TEN_B -> 12
                FIVE_TEN_C -> 13
                FIVE_TEN_D -> 14
                FIVE_ELEVEN_A -> 15
                FIVE_ELEVEN_B -> 16
                FIVE_ELEVEN_C -> 17
                FIVE_ELEVEN_D -> 18
                FIVE_TWELVE_A -> 19
                FIVE_TWELVE_B -> 20
                FIVE_TWELVE_C -> 21
                FIVE_TWELVE_D -> 22
                FIVE_THIRTEEN_A -> 23
                FIVE_THIRTEEN_B -> 24
                FIVE_THIRTEEN_C -> 25
                FIVE_THIRTEEN_D -> 26
                FIVE_FOURTEEN_A -> 27
                FIVE_FOURTEEN_B -> 28
                FIVE_FOURTEEN_C -> 29
                FIVE_FOURTEEN_D -> 30
                FIVE_FIFTEEN_A -> 31
                FIVE_FIFTEEN_B -> 32
                FIVE_FIFTEEN_C -> 33
                FIVE_FIFTEEN_D -> 34
            }
        }

        fun numberToGrade(number: Int): USAEnum? {
            return when (number) {
                1 -> FIVE_ZERO
                2 -> FIVE_ONE
                3 -> FIVE_TWO
                4 -> FIVE_THREE
                5 -> FIVE_FOUR
                6 -> FIVE_FIVE
                7 -> FIVE_SIX
                8 -> FIVE_SEVEN
                9 -> FIVE_EIGHT
                10 -> FIVE_NINE
                11 -> FIVE_TEN_A
                12 -> FIVE_TEN_B
                13 -> FIVE_TEN_C
                14 -> FIVE_TEN_D
                15 -> FIVE_ELEVEN_A
                16 -> FIVE_ELEVEN_B
                17 -> FIVE_ELEVEN_C
                18 -> FIVE_ELEVEN_D
                19 -> FIVE_TWELVE_A
                20 -> FIVE_TWELVE_B
                21 -> FIVE_TWELVE_C
                22 -> FIVE_TWELVE_D
                23 -> FIVE_THIRTEEN_A
                24 -> FIVE_THIRTEEN_B
                25 -> FIVE_THIRTEEN_C
                26 -> FIVE_THIRTEEN_D
                27 -> FIVE_FOURTEEN_A
                28 -> FIVE_FOURTEEN_B
                29 -> FIVE_FOURTEEN_C
                30 -> FIVE_FOURTEEN_D
                31 -> FIVE_FIFTEEN_A
                32 -> FIVE_FIFTEEN_B
                33 -> FIVE_FIFTEEN_C
                34 -> FIVE_FIFTEEN_D
                else -> null
            }
        }

        fun getList(): List<String> {
            return values().map {
                it.toString()
            }
        }
    }


}