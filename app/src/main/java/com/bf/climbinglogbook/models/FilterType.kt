package com.bf.climbinglogbook.models

data class FilterType(
    var none: Boolean = true,
    var os: Boolean = false,
    var rp: Boolean = false,
    var flash: Boolean = false,
    var trad: Boolean = false,
    var sport: Boolean = false
) {
    fun isFilterActive(): Boolean {
        return os || rp || flash || trad || sport
    }

    fun isClimbingTypeFilterActive() = trad || sport

    fun isAscentTypeFilterActive() = os || rp || flash
}