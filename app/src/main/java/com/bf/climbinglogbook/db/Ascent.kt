package com.bf.climbinglogbook.db

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bf.climbinglogbook.models.BelayType
import com.bf.climbinglogbook.models.ClimbingType
import com.bf.climbinglogbook.models.AscentStyle
import com.bf.climbinglogbook.models.GradeSystem
import com.bf.climbinglogbook.other.Constants.ROUTES_TABLE_NAME

@Entity(tableName = ROUTES_TABLE_NAME)
data class Ascent (
    var name : String,
    var grade : Int = 0,
    var country : String? = null,
    var region : String? = null,
    var rock : String? = null,
    var lat : Long = 0L,
    var lng : Long = 0L,
    var comment : String? = null,
    var img : Bitmap? = null,
    var meters : Int = 0,
    var gradeType : GradeSystem = GradeSystem.FRENCH,
    var belayType : BelayType? = null,
    var climbingType : ClimbingType? = null,
    var belayer : String? = null,
    var ascentStyle : AscentStyle,
    var pitches : Int = 1,
    var hard : Boolean = false
    ){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}