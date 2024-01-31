package com.smilegate.Easel.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smilegate.Easel.databinding.ItemHighlightBinding
import com.smilegate.Easel.domain.model.HighlightItem

class HighlightAdapter(private val context: Context, private val highlightList: List<HighlightItem>) :
    RecyclerView.Adapter<HighlightAdapter.HighlightViewHolder>() {

    inner class HighlightViewHolder(private val binding: ItemHighlightBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HighlightItem) {

            binding.textView8.text = item.title.toString()
            binding.textView9.text = item.description.toString()
            binding.btnText.text = item.btnText.toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighlightViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHighlightBinding.inflate(inflater, parent, false)
        return HighlightViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HighlightViewHolder, position: Int) {
        val highlightItem = highlightList[position]
        holder.bind(highlightItem)
    }

    override fun getItemCount(): Int {
        return highlightList.size
    }

}
