package com.bf.climbinglogbook.models

import android.content.Context
import com.bf.climbinglogbook.R

enum class BelayType {
    LEAD,
    TOP_ROPE,
    SOLO_LEAD,
    SOLO_TOP_ROPE,
    FREE_SOLO;

    fun getLabel(context: Context): String {
        return when (this) {
            LEAD -> context.getString(R.string.belay_type_lead)
            TOP_ROPE -> context.getString(R.string.belay_top_rope)
            SOLO_LEAD -> context.getString(R.string.belay_solo_lead)
            SOLO_TOP_ROPE -> context.getString(R.string.belay_solo_top_rope)
            FREE_SOLO -> context.getString(R.string.belay_free_solo)
        }
    }


}