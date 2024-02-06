package com.smilegate.Easel.presentation.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smilegate.Easel.databinding.ItemPostImgBinding

class PostImgAdapter(private val imageList: List<String>) :
    RecyclerView.Adapter<PostImgAdapter.PostImgViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostImgViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPostImgBinding.inflate(inflater, parent, false)
        return PostImgViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostImgViewHolder, position: Int) {
        val imageUri = imageList[position]
        holder.bind(imageUri)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class PostImgViewHolder(private val binding: ItemPostImgBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            val shapeDrawable = GradientDrawable()
            shapeDrawable.shape = GradientDrawable.RECTANGLE
            shapeDrawable.cornerRadius = 16f // 모서리 둥글기 설정
            shapeDrawable.setStroke(2, Color.BLACK) // 테두리 설정
            binding.ivPostImg.background = shapeDrawable
        }

        fun bind(imageUri: String) {
            // 이미지 로드 및 설정
            Glide.with(binding.ivPostImg.context)
                .load(imageUri)
                .into(binding.ivPostImg)
        }
    }
}
