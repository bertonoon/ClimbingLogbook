package com.bf.climbinglogbook.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.chart.common.listener.Event
import com.anychart.chart.common.listener.ListenersInterface
import com.anychart.enums.Align
import com.anychart.enums.LegendLayout
import com.bf.climbinglogbook.R
import com.bf.climbinglogbook.databinding.FragmentStatisticsBinding
import com.bf.climbinglogbook.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()
    private val statisticsViewModel: StatisticsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        binding.toolbar.title.text = getString(R.string.title_statistics)
        //initListeners()
        setupBarChart()
        return binding.root
    }

    private fun initListeners() {

    }

    private fun setupBarChart() {
        val pieChart = AnyChart.pie()

        val data: MutableList<DataEntry> = mutableListOf()
        data.add(ValueDataEntry("Apples", 6371664))
        data.add(ValueDataEntry("Pears", 789622))
        data.add(ValueDataEntry("Bananas", 7216301))
        data.add(ValueDataEntry("Grapes", 1486621))
        data.add(ValueDataEntry("Oranges", 1200000))

        pieChart.data(data)
        pieChart.title("Test")
        pieChart.labels().position("outside")
        pieChart.legend().enabled(true)
        pieChart.legend().title().text("legend").padding(0.0, 0.0, 10.0, 0.0)
        pieChart.legend().position("center-bottom").itemsLayout(LegendLayout.HORIZONTAL)
            .align(Align.CENTER)

        binding.anyChartView.setChart(pieChart)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
