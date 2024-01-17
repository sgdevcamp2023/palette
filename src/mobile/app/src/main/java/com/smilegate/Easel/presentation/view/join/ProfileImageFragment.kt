package com.smilegate.Easel.presentation.view.join

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentProfileImageBinding
import com.smilegate.Easel.databinding.FragmentStartBinding
import de.hdodenhof.circleimageview.CircleImageView

class ProfileImageFragment : Fragment() {
    private lateinit var _binding: FragmentProfileImageBinding
    private val binding get() = _binding!!

    // 이미지 선택 요청 코드
    private val PICK_IMAGE_REQUEST = 1

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileImageBinding.inflate(inflater, container, false)

        // 갤러리 아이콘 클릭 시 이미지 선택 요청
        binding.profileImageFragmentCameraBtn.setOnClickListener {
            openGallery()
        }

        navController = findNavController()

        binding.profileImageFragmentNextBtn.setOnClickListener {
            navController.navigate(R.id.action_profileImageFragment_to_askNameFragment)
        }

        binding.profileImageFragmentSkipBtn.setOnClickListener {
            navController.navigate(R.id.action_profileImageFragment_to_askNameFragment)
        }

        return binding.root
    }

    private fun openGallery() {
        // 갤러리에서 이미지를 선택하는 Intent 생성
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            // 이미지가 성공적으로 선택되었을 때
            val selectedImageUri: Uri? = data?.data

            // 선택한 이미지를 ImageView에 설정
            selectedImageUri?.let {
                binding.profileImage.setImageURI(it)
                binding.profileImage.scaleType = ImageView.ScaleType.CENTER_CROP
            }

            //Camera Button 숨기기
            binding.profileImageFragmentCameraBtn.visibility = View.GONE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}
