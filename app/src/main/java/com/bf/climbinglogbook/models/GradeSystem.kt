package com.bf.climbinglogbook.models

import android.content.Context
import com.bf.climbinglogbook.R

enum class GradeSystem {
    FRENCH,
    KURTYKA,
    USA,
    UIAA;

    fun getLabel(context: Context): String {
        return when (this) {
            FRENCH -> context.getString(R.string.french_grade_system)
            KURTYKA -> context.getString(R.string.kurtyka_grade_system)
            USA -> context.getString(R.string.usa_grade_system)
            UIAA -> context.getString(R.string.uiaa_grade_system)
        }
    }
}