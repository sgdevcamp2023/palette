package com.smilegate.Easel.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smilegate.Easel.databinding.ItemTimelineBinding
import com.smilegate.Easel.domain.model.TimelineItem

class TimelineRecyclerViewAdapter(private val timelineList: List<TimelineItem>) :
    RecyclerView.Adapter<TimelineRecyclerViewAdapter.TimelineViewHolder>() {

    inner class TimelineViewHolder(private val binding: ItemTimelineBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TimelineItem) {
            binding.ivTimelineProfileImg.setImageResource(item.profileImg.toInt())
            binding.tvTimelineNickname.text = item.nickName
            binding.tvTimelineUsername.text = item.userName
            binding.tvTimelineTime.text = item.timeAgo
            binding.tvTimelineContent.text = item.content
            binding.ivTimelineContentImg.setImageResource(item.contentImg.toInt())
            binding.tvTimelineHashtag.text = item.hashtag
            binding.tvTimelineMention.text = item.replys.toString()
            binding.tvTimelineRetweet.text = item.reposts.toString()
            binding.tvTimelineLike.text = item.like.toString()
            binding.tvTimelineViews.text = item.views.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTimelineBinding.inflate(inflater, parent, false)
        return TimelineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        val item = timelineList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return timelineList.size
    }
}
