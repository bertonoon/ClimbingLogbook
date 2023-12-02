package com.bf.climbinglogbook.models

import android.content.Context
import com.bf.climbinglogbook.R

enum class ClimbingType {
    SPORT,
    TRAD,
    BOULDERING,
    DRYTOOLING,
    MIX,
    ALPINE,
    ICE;

    fun getLabel(context: Context): String {
        return when (this) {
            SPORT -> context.getString(R.string.climbing_style_sport)
            TRAD -> context.getString(R.string.climbing_style_trad)
            BOULDERING -> context.getString(R.string.climbing_style_bouldering)
            DRYTOOLING -> context.getString(R.string.climbing_style_drytooling)
            MIX -> context.getString(R.string.climbing_style_mix)
            ALPINE -> context.getString(R.string.climbing_style_alpine)
            ICE -> context.getString(R.string.climbing_style_ice)
        }
    }
}