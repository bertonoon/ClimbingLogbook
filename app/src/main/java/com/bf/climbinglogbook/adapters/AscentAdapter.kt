package com.bf.climbinglogbook.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bf.climbinglogbook.databinding.ItemSingleAscentBinding
import com.bf.climbinglogbook.db.Ascent
import com.bf.climbinglogbook.other.Constants
import com.bf.climbinglogbook.repositories.GradesRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AscentAdapter : RecyclerView.Adapter<AscentAdapter.AscentViewHolder>() {

    inner class AscentViewHolder(val binding: ItemSingleAscentBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Ascent>() {
        override fun areItemsTheSame(oldItem: Ascent, newItem: Ascent): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Ascent, newItem: Ascent): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Ascent>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AscentViewHolder {
        val binding = ItemSingleAscentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AscentViewHolder(binding)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: AscentViewHolder, position: Int) {
        val ascent = differ.currentList[position]
        val grades = GradesRepository().getGradesMap()


        holder.itemView.apply {
            val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())
            val grade = grades[ascent.originalGradeSystem]?.get(ascent.originalGradeOrdinal)

            holder.binding.apply {
                tvTitle.text = ascent.name
                tvSecondTitle.text = dateFormat.format(ascent.date)
                tvGrade.text = grade.toString()
            }
        }
    }


}