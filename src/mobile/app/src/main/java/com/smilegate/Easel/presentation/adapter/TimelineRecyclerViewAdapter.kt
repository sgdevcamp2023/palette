package com.smilegate.Easel.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smilegate.Easel.databinding.ItemTimelineBinding
import com.smilegate.Easel.domain.model.TimelineItem

class TimelineRecyclerViewAdapter(private val timelineList: List<TimelineItem>) :
    RecyclerView.Adapter<TimelineRecyclerViewAdapter.TimelineViewHolder>() {

    inner class TimelineViewHolder(private val binding: ItemTimelineBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TimelineItem) {
            item.profileImg?.let { binding.ivTimelineProfileImg.setImageResource(it) }
            binding.tvTimelineNickname.text = item.nickName
            binding.tvTimelineUsername.text = item.userName
            binding.tvTimelineTime.text = item.timeAgo
            binding.tvTimelineContent.text = item.content
            item.contentImg?.let { binding.ivTimelineContentImg.setImageResource(it) }
            binding.tvTimelineHashtag.text = item.hashtag
            binding.tvTimelineMention.text = item.replys.toString()
            binding.tvTimelineRetweet.text = item.reposts.toString()
            binding.tvTimelineLike.text = item.like.toString()
            binding.tvTimelineViews.text = item.views.toString()

            binding.ivTimelineContentImg.setVisibleOrGone(item.contentImg != null)
            binding.tvTimelineHashtag.setVisibleOrGone(item.hashtag != null)
            binding.tvTimelineMention.setVisibleOrGone(item.replys != null)
            binding.tvTimelineRetweet.setVisibleOrGone(item.reposts != null)
            binding.tvTimelineLike.setVisibleOrGone(item.like != null)
            binding.tvTimelineViews.setVisibleOrGone(item.views != null)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTimelineBinding.inflate(inflater, parent, false)
        return TimelineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        val timelineItem = timelineList[position]
        holder.bind(timelineItem)
    }

    override fun getItemCount(): Int {
        return timelineList.size
    }

    fun View.setVisibleOrGone(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun View.setVisibleOrInvisible(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }
}
