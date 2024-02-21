package com.smilegate.Easel.presentation.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.smilegate.Easel.MainActivity
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.ItemQuoteImageBinding
import com.smilegate.Easel.databinding.ItemQuoteTextBinding
import com.smilegate.Easel.databinding.ItemTimelineBinding
import com.smilegate.Easel.domain.model.TimelineItem
import com.smilegate.Easel.presentation.TimelinePopupManager
import com.smilegate.Easel.presentation.view.timeline.RetweetBottomSheetDialog
import com.smilegate.Easel.presentation.view.timeline.ShareBottomSheetDialog
import com.smilegate.Easel.presentation.view.timeline.ViewBottomSheetDialog

class TimelineRecyclerViewAdapter(private val context: Context, private val timelineList: List<TimelineItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val popupManager = TimelinePopupManager(context)
    private var timelineListItem: MutableList<TimelineItem> = mutableListOf()

    // 뷰 유형 상수 정의
    companion object {
        const val VIEW_TYPE_ORIGINAL = 0
        const val VIEW_TYPE_TEXT = 1
        const val VIEW_TYPE_IMAGE = 2
    }

    fun updateData(newList: List<TimelineItem>) {
        timelineListItem = newList.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        // 각 아이템의 뷰 타입 반환
        return when (position % 3) {
            0 -> VIEW_TYPE_ORIGINAL
            1 -> VIEW_TYPE_TEXT
            2 -> VIEW_TYPE_IMAGE
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

    inner class TimelineViewHolder(private val binding: ItemTimelineBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val icElseActions: ImageView = binding.root.findViewById(R.id.ic_else_actions)
        private val icViews: ImageView = binding.root.findViewById(R.id.ic_timeline_views)
        private val icRetweet: ImageView = binding.root.findViewById(R.id.ic_timeline_retweet)
        private val icShare: ImageView = binding.root.findViewById(R.id.ic_timeline_share)

        init {
            icElseActions.setOnClickListener {
                popupManager.showPopupMenu(icElseActions)
            }

            icRetweet.setOnClickListener {
                retweetBottomSheet()
            }

            icShare.setOnClickListener {
                shareBottomSheet()
            }

            icViews.setOnClickListener {
                viewBottomSheet()
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
    @RequiresApi(Build.VERSION_CODES.S)
    inner class QuoteTextViewHolder(private val binding: ItemQuoteTextBinding) :
        RecyclerView.ViewHolder(binding.root) {

            private val icElseActions: ImageView = binding.root.findViewById(R.id.ic_else_actions)
            private val icViews: ImageView = binding.root.findViewById(R.id.ic_timeline_views)
            private val icRetweet: ImageView = binding.root.findViewById(R.id.ic_timeline_retweet)
            private val icShare: ImageView = binding.root.findViewById(R.id.ic_timeline_share)

            init {
                icElseActions.setOnClickListener {
                    popupManager.showPopupMenu(icElseActions)
                }

                icRetweet.setOnClickListener {
                    retweetBottomSheet()
                }

                icShare.setOnClickListener {
                    shareBottomSheet()
                }

                icViews.setOnClickListener {
                    viewBottomSheet()
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

                item.quoteProfileImg?.let { binding.ivQuoteProfileImg.setImageResource(it)}
                binding.tvQuoteNickname.text = item.quoteNickName
                binding.tvQuoteUsername.text = item.quoteUserName
                binding.tvQuoteTime.text = item.quoteTimeAgo
                binding.tvQuoteText.text = item.quoteContent
                item.quoteContentImg?.let { binding.ivQuoteContentImg.setImageResource(it) }

                binding.tvTimelineContent.setVisibleOrGone(!item.content.isNullOrEmpty())
                binding.ivTimelineContentImg.setVisibleOrGone(item.contentImg != null)
                binding.ivTimelineContentCard.setVisibleOrGone(item.contentImg != null)

                binding.quoteCardView.setVisibleOrGone(item.viewType != VIEW_TYPE_ORIGINAL)
                binding.ivQuoteProfileImg.setVisibleOrGone(item.quoteProfileImg != null)
                binding.tvQuoteNickname.setVisibleOrGone(item.quoteNickName != null)
                binding.tvQuoteUsername.setVisibleOrGone(item.quoteUserName != null)
                binding.tvQuoteTime.setVisibleOrGone(item.quoteTimeAgo != null)
                binding.tvQuoteText.setVisibleOrGone(item.quoteContent != null)
                binding.ivQuoteContentImg.setVisibleOrGone(item.quoteContentImg != null)

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

    inner class QuoteImageViewHolder(private val binding: ItemQuoteImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val icElseActions: ImageView = binding.root.findViewById(R.id.ic_else_actions)
        private val icViews: ImageView = binding.root.findViewById(R.id.ic_timeline_views)
        private val icRetweet: ImageView = binding.root.findViewById(R.id.ic_timeline_retweet)
        private val icShare: ImageView = binding.root.findViewById(R.id.ic_timeline_share)

        init {
            icElseActions.setOnClickListener {
                popupManager.showPopupMenu(icElseActions)
            }

            icRetweet.setOnClickListener {
                retweetBottomSheet()
            }

            icShare.setOnClickListener {
                shareBottomSheet()
            }

            icViews.setOnClickListener {
                viewBottomSheet()
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

            item.quoteProfileImg?.let { binding.ivQuoteProfileImg.setImageResource(it)}
            binding.tvQuoteNickname.text = item.quoteNickName
            binding.tvQuoteUsername.text = item.quoteUserName
            binding.tvQuoteTime.text = item.quoteTimeAgo
            binding.tvQuoteText.text = item.quoteContent
            item.quoteContentImg?.let { binding.ivQuoteProfileImg.setImageResource(it) }

            binding.tvTimelineContent.setVisibleOrGone(!item.content.isNullOrEmpty())
            binding.ivTimelineContentImg.setVisibleOrGone(item.contentImg != null)
            binding.ivTimelineContentCard.setVisibleOrGone(item.contentImg != null)

            binding.quoteCardView.setVisibleOrGone(item.viewType != VIEW_TYPE_ORIGINAL)
            binding.ivQuoteProfileImg.setVisibleOrGone(item.quoteProfileImg != null)
            binding.tvQuoteNickname.setVisibleOrGone(item.quoteNickName != null)
            binding.tvQuoteUsername.setVisibleOrGone(item.quoteUserName != null)
            binding.tvQuoteTime.setVisibleOrGone(item.quoteTimeAgo != null)
            binding.tvQuoteText.setVisibleOrGone(item.quoteContent != null)
            binding.ivQuoteContentImg.setVisibleOrGone(item.quoteContentImg != null)

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

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ORIGINAL -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemTimelineBinding.inflate(inflater, parent, false)
                TimelineViewHolder(binding)
            }
            VIEW_TYPE_TEXT -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemQuoteTextBinding.inflate(inflater, parent, false)
                QuoteTextViewHolder(binding)
            }
            VIEW_TYPE_IMAGE -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemQuoteImageBinding.inflate(inflater, parent, false)
                QuoteImageViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val timelineItem = timelineList[position]
        when (holder) {
            is TimelineViewHolder -> {
                holder.bind(timelineItem)
            }
            is QuoteTextViewHolder -> {
                holder.bind(timelineItem)
            }
            is QuoteImageViewHolder -> {
                holder.bind(timelineItem)
            }
        }
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

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        popupManager.dismissPopupMenu()
    }

    private fun retweetBottomSheet() {
        val modal = RetweetBottomSheetDialog()
        modal.show((context as MainActivity).supportFragmentManager, "BasicBottomModalSheet")
    }

    private fun shareBottomSheet() {
        val modal = ShareBottomSheetDialog()
        modal.show((context as MainActivity).supportFragmentManager, "BasicBottomModalSheet")
    }

    private fun viewBottomSheet() {
        val modal = ViewBottomSheetDialog()
        modal.show((context as MainActivity).supportFragmentManager, "BasicBottomModalSheet")
    }
}
