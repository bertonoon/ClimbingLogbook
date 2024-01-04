package com.bf.climbinglogbook.ui.statistics

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bf.climbinglogbook.R
import com.bf.climbinglogbook.databinding.FragmentStatisticsBinding
import com.bf.climbinglogbook.models.GradeSystem
import com.bf.climbinglogbook.models.PieChartGrade
import com.bf.climbinglogbook.ui.MainViewModel
import com.bf.climbinglogbook.utils.CustomMarkerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StatisticsFragment : Fragment(), OnChartValueSelectedListener {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()
    private val statisticsViewModel: StatisticsViewModel by viewModels()
    private lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        binding.toolbar.title.text = getString(R.string.title_statistics)
        setPieChartSettings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObservers()
        //initListeners()
    }

    private fun initListeners() {

    }

    private fun initObservers() {
        statisticsViewModel.ascentsSortedByGradeAsc?.observe(viewLifecycleOwner) {
            setPieChartSettings()
            setPieChartData(
                statisticsViewModel.calculateAllAscentsToFrench(it),
                GradeSystem.FRENCH
            )
        }
    }

    private fun setPieChartData(gradeCountList: List<PieChartGrade>, gradeSystem: GradeSystem) {
        val dataMPA: MutableList<PieEntry> = mutableListOf<PieEntry>().apply {
            for (i in gradeCountList.indices) {
                if (gradeCountList[i].gradeCount > 0) {
                    add(
                        PieEntry(
                            gradeCountList[i].gradeCount.toFloat(),
                            gradeCountList[i].gradeName
                        )
                    )
                }
            }
        }
        val dataSet = PieDataSet(dataMPA, gradeSystem.getLabel(requireContext())).apply {
            setDrawIcons(false)
            sliceSpace = 3f
            iconsOffset = MPPointF(0f, 40f)
            selectionShift = 5f
            valueTextSize = 14f
            xValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
            yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
            valueFormatter = DefaultValueFormatter(0)
            isHighlightEnabled = true
        }

        val colors = ArrayList<Int>()
        for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colors.add(c)
        colors.add(ColorTemplate.getHoloBlue())

        dataSet.colors = colors

        val data = PieData(dataSet)

        pieChart.data = data
        pieChart.invalidate()
    }

    private fun setPieChartSettings() {
        pieChart = binding.pieChart.apply {
            setUsePercentValues(false)
            description.isEnabled = false
            setExtraOffsets(20f, 10f, 20f, 5f)
            dragDecelerationFrictionCoef = 0.95f
            setCenterTextTypeface(Typeface.DEFAULT)
            setDrawMarkers(true)
            marker = CustomMarkerView(requireContext(),R.layout.custom_marker_view)
//            setCenterText(generateCenterSpannableText())
            isDrawHoleEnabled = true
            setHoleColor(Color.WHITE)
            setTransparentCircleColor(Color.WHITE)
            setTransparentCircleAlpha(110)
            holeRadius = 50f
            transparentCircleRadius = 53f
            setDrawCenterText(true)
            rotationAngle = 0f
            isRotationEnabled = true
            isHighlightPerTapEnabled = true
            setOnChartValueSelectedListener(this@StatisticsFragment)
            animateY(1400, Easing.EaseInOutQuad)
        }

        pieChart.legend.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.TOP
            horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            orientation = Legend.LegendOrientation.VERTICAL
            setDrawInside(false)
            xEntrySpace = 10f
            textSize = 14f
            yEntrySpace = 0f
            yOffset = 0f
        }

        pieChart.apply {
            setEntryLabelColor(Color.BLACK)
            setEntryLabelTypeface(Typeface.DEFAULT)
            setEntryLabelTextSize(14f)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        if (e == null)
            return;
        Log.i(
            "PieChart",
            "Value: " + e.y + ", index: " + h?.x
                    + ", DataSet index: " + h?.dataSetIndex
        );
//        pieChart.highlightValue(e.x, h!!.dataSetIndex)
//        h?.dataSetIndex?.let { pieChart.highlightValue(e.y, it) }
    }

    override fun onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }
}
