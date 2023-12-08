package com.bf.climbinglogbook.models

enum class AddAscentErrors {
    NONE,
    NULL_OR_EMPTY_NAME,
    TO_LONG_NAME,
    NULL_DATE,
    DATE_FROM_FUTURE,
    NULL_GRADE_SYSTEM,
    NULL_ASCENT_STYLE,
    ERROR_CONV_TO_USA,
    ERROR_GRADE_ORDINAL,
    TO_LONG_OR_EMPTY_TEXT
}