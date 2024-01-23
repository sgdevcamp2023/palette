package com.smilegate.Easel.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.ItemTimelineBinding
import com.smilegate.Easel.domain.model.TimelineItem

class TimelineRecyclerViewAdapter(private val timelineList: List<TimelineItem>) :
    RecyclerView.Adapter<TimelineRecyclerViewAdapter.TimelineViewHolder>() {

    inner class TimelineViewHolder(private val binding: ItemTimelineBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val icElseActions: ImageView = binding.root.findViewById(R.id.ic_else_actions)
        private val icViews: ImageView = binding.root.findViewById(R.id.ic_timeline_views)
        private val icRetweet: ImageView = binding.root.findViewById(R.id.ic_timeline_retweet)
        private val icShare: ImageView = binding.root.findViewById(R.id.ic_timeline_share)

        init {
            // ic_else_actions 아이콘에 클릭 리스너 설정
            icElseActions.setOnClickListener {
                // 아이콘을 클릭했을 때의 동작 구현
                // 여기에 원하는 동작을 추가하면 됩니다.
                showPopupMenu(icElseActions)  // 예시: 팝업 메뉴 표시 함수 호출
            }
        }

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
            binding.ivTimelineContentCard.setVisibleOrGone(item.contentImg != null)

            binding.tvTimelineHashtag.setVisibleOrGone(!item.hashtag.isNullOrEmpty())
            binding.tvTimelineMention.setVisibleOrInvisible(item.replys != null)
            binding.tvTimelineRetweet.setVisibleOrInvisible(item.reposts != null)
            binding.tvTimelineLike.setVisibleOrInvisible(item.like != null)
            binding.tvTimelineViews.setVisibleOrInvisible(item.views != null)

            // Glide를 사용하여 프로필 이미지 로드
            item.profileImg?.let {
                Glide.with(binding.root.context)
                    .load(it)
                    .apply(RequestOptions.circleCropTransform())
                    .into(binding.ivTimelineProfileImg)
            }

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

    private fun showPopupMenu(anchorView: View) {
        // 팝업 메뉴를 표시하는 코드를 작성
        val popupMenu = PopupMenu(anchorView.context, anchorView)
        popupMenu.inflate(R.menu.timeline_popup_menu)

         // 팝업 메뉴 아이템 클릭 리스너 설정
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.popup_no_interest -> {
                    true
                }
                R.id.popup_is_follow -> {
                    true
                }
                R.id.popup_add_list -> {
                    true
                }
                R.id.popup_mute -> {
                    true
                }
                R.id.popup_block -> {
                    true
                }
                R.id.popup_report -> {
                    true
                }
                else -> false
            }
        }

        // 팝업 메뉴 표시
        popupMenu.show()
    }
}
