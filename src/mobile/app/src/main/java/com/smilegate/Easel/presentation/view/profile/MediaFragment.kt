package com.smilegate.Easel.presentation.view.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.smilegate.Easel.MainActivity
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentMediaBinding
import com.smilegate.Easel.databinding.FragmentReplyBinding
import com.smilegate.Easel.domain.model.TimelineItem
import com.smilegate.Easel.presentation.adapter.TimelineRecyclerViewAdapter

class MediaFragment : Fragment() {
    private lateinit var binding: FragmentMediaBinding

    private lateinit var navController: NavController

    private var savedScrollPosition: Int = 0

    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMediaBinding.inflate(inflater, container, false)

        val toolbar = requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_container)
        toolbar.visibility = View.GONE

        navController = findNavController()

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // MainActivity의 참조를 가져옴
        if (context is MainActivity) {
            mainActivity = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.requestFocus()

        val mediaList = generateDummyTimelineData()

        val adapter = TimelineRecyclerViewAdapter(requireContext(), mediaList)
        binding.rvTimeline.adapter = adapter
        binding.rvTimeline.layoutManager = LinearLayoutManager(requireContext())

        // 스크롤 리스너를 이용하여 스크롤 위치 저장
        binding.ScrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            savedScrollPosition = scrollY
        }

        // 저장된 스크롤 위치 복원
        binding.ScrollView.post {
            binding.ScrollView.scrollTo(0, savedScrollPosition)
        }
    }

    private fun generateDummyTimelineData(): List<TimelineItem> {
        val profileImgId = R.drawable.sample_profile_img5
        val profileImgId1 = R.drawable.sample_profile_img1
        val profileImgId2 = R.drawable.sample_profile_img2
        val profileImgId3 = R.drawable.sample_profile_img3
        val profileImgId4 = R.drawable.sample_profile_img4

        val contentImgId = R.drawable.sample_content_img1
        val contentImgId1 = R.drawable.sample_content_img2
        val contentImgId2 = R.drawable.sample_content_img3
        val contentImgId3 = R.drawable.sample_content_img4

        return listOf(
            TimelineItem(profileImgId, "이원영", "@courtney81819", "1시간",
                "아 슈뢰딩거가 아닌가?ㅋ", null, null,
                2, 1, null, 24),


            TimelineItem(profileImgId2, "이상민", "@isangmi92157279", "32분",
                "비가 내리는 날이에요.\n추적이는 바닥을 보며 걷다보니 카페가 나와 커피를 사 봤어요.", contentImgId, null,
                4, 2, 5, 121),

            TimelineItem(profileImgId1, "박희원", "@_Parking1_", "18분",
                "타래 스타트", contentImgId1, null,
                1, 1, null, 114),

            TimelineItem(profileImgId4, "김도율", "@doxxx93", "8분",
                "테스트", null, null,
                1, null, 2, 32),

            TimelineItem(profileImgId2, "이상민", "@isangmi92157279", "1주",
                "커피는 아이스 아메리카노에요.\n컵에 스며든 물방울처럼, 제 마음을 촉촉하게 만들어 주네요..^^", contentImgId2, null,
                3, 4, 2, 89),

            TimelineItem(profileImgId3, "김도현", "@KittenDiger", "4일",
                "테스트 인용의 인용", null, null,
                1, null, null, 30),

            TimelineItem(profileImgId, "이원영", "@courtney81819", "3시간",
                "청하 로제", contentImgId3, null,
                3, null, 2, 334),

            TimelineItem(profileImgId3, "김도현", "@KittenDiger", "4일",
                "구글인ㅇㅎㅇ의 인용의 인용", null, null,
                1, 2, null, 50),
        )

    }

    override fun onPause() {
        super.onPause()
        savedScrollPosition = binding.ScrollView.scrollY
    }
}
