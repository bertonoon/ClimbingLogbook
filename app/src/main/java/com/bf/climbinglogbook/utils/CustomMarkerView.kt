package com.bf.climbinglogbook.utils

import android.content.Context
import android.view.View
import android.widget.TextView
import com.bf.climbinglogbook.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF





class CustomMarkerView(context: Context, layoutResource: Int) :
    MarkerView(context, layoutResource) {

    private val tvContent: TextView = findViewById<View>(R.id.tvMarkerViewContent) as TextView

    override fun refreshContent(e: Entry, highlight: Highlight?) {
        tvContent.text = "${e.y.toInt()}"
        super.refreshContent(e, highlight);

    }

    private var mOffset: MPPointF? = null
    override fun getOffset(): MPPointF? {
        if (mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = MPPointF(-(width / 2).toFloat(), -height.toFloat())
        }
        return mOffset
    }

}