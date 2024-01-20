package com.smilegate.Easel.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
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
            binding.tvTimelineMention.text = item.replys?.toString() ?: "0"
            binding.tvTimelineRetweet.text = item.reposts?.toString() ?: "0"
            binding.tvTimelineLike.text = item.like?.toString() ?: "0"
            binding.tvTimelineViews.text = item.views?.toString() ?: "0"

            binding.tvTimelineContent.setVisibleOrGone(!item.content.isNullOrEmpty())
            binding.ivTimelineContentImg.setVisibleOrGone(item.contentImg != null)

            binding.tvTimelineHashtag.setVisibleOrGone(!item.hashtag.isNullOrEmpty())
            binding.tvTimelineMention.setVisibleOrInvisible(item.replys != null)
            binding.tvTimelineRetweet.setVisibleOrInvisible(item.reposts != null)
            binding.tvTimelineLike.setVisibleOrInvisible(item.like != null)
            binding.tvTimelineViews.setVisibleOrInvisible(item.views != null)

            // Glide를 사용하여 프로필 이미지 로드
            Glide.with(binding.root.context)
                .load(item.profileImg)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivTimelineProfileImg)

            // contentImgUrl이 null이 아닌 경우에만 Glide를 사용하여 이미지 로드
            item.contentImg?.let {
                Glide.with(binding.root.context)
                    .load(it)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.ivTimelineContentImg)
            }
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
