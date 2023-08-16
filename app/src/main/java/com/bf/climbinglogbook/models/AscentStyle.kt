package com.bf.climbinglogbook.models

import android.content.Context
import com.bf.climbinglogbook.R

enum class AscentStyle {
    ON_SIGHT,
    REDPOINT,
    FLASH,
    PINKPOINT,
    GREENPOINT;


    fun getLabel(context: Context) :String{
        return when (this){
            AscentStyle.ON_SIGHT -> context.getString(R.string.ascent_style_onsight)
            AscentStyle.REDPOINT -> context.getString(R.string.ascent_style_redpoint)
            AscentStyle.FLASH -> context.getString(R.string.ascent_style_flash)
            AscentStyle.PINKPOINT -> context.getString(R.string.ascent_style_pinkpoint)
            AscentStyle.GREENPOINT -> context.getString(R.string.ascent_style_greenpoint)
        }
    }
    
    fun getShortcut() : String{
        return when(this){
            ON_SIGHT -> "OS"
            REDPOINT -> "RP"
            FLASH -> "FL"
            PINKPOINT -> "PP"
            GREENPOINT -> "GP"
        }
    }

}