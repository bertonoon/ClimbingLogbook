package com.bf.climbinglogbook.models.gradeEnums

enum class KurtykaEnum() {
    ZERO,
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
    SIX_EIGHT_PLUS;

    override fun toString(): String {
        return when(this){
            ZERO -> "0"
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
        }
    }
}