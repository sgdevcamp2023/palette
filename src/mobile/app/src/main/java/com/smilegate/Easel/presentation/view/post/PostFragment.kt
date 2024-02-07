package com.smilegate.Easel.presentation.view.post

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.smilegate.Easel.R
import com.smilegate.Easel.databinding.FragmentPostBinding
import com.smilegate.Easel.presentation.adapter.PostImgAdapter


class PostFragment : Fragment() {
    private lateinit var binding: FragmentPostBinding

    private lateinit var navController: NavController

    private var isViewVisible: Boolean = true

    private val permissionRequestCode = 100
    private val pickImageRequest = 1

    private val imageList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPostBinding.inflate(inflater, container, false)

        val toolbar =
            requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_container)
        toolbar.visibility = GONE

        val bottomNavigation = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigation?.visibility = GONE

        val editPageIcon = listOf(
            binding.ivSpace,
            binding.icSpaceIcon,
            binding.ivCamera,
            binding.icCameraIcon,
            binding.tvTempStorage,
            binding.rvPostImg,
        )

        binding.etPostContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty() && !isViewVisible) {

                    for (view in editPageIcon) {
                        view.visibility = VISIBLE
                    }

                    isViewVisible = true

                    binding.btnPost.isEnabled = false
                    //binding.btnPost.resources.getResourceName(R.drawable.btn_post_fragment)

                    val textColorResourceId = R.color.Blue_200
                    val textColor = ContextCompat.getColor(requireContext(), textColorResourceId)
                    binding.btnPost.setTextColor(textColor)

                } else if (!s.isNullOrEmpty() && isViewVisible) {

                    for (view in editPageIcon) {
                        view.visibility = INVISIBLE
                    }

                    isViewVisible = false

                    binding.btnPost.isEnabled = true
                    //binding.btnPost.resources.getResourceName(R.drawable.btn_post_fragment_enabled)

                    val textColorResourceId = R.color.white
                    val textColor = ContextCompat.getColor(requireContext(), textColorResourceId)
                    binding.btnPost.setTextColor(textColor)
                }

                s?.let {
                    val currentLength = it.length
                    val progress = currentLength.toFloat()
                    binding.etProgressbar.setProgressWithAnimation(progress)
                }
            }
        })

        binding.icGallery.setOnClickListener {
            openGallery()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        val editText = view.findViewById<EditText>(R.id.et_post_content)
        editText.requestFocus()

        binding.ivBackBtn.setOnClickListener {
            editText.clearFocus()
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editText.windowToken, 0)

            navController.navigate(R.id.action_postFragment_to_timelineFragment)
        }

        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_MEDIA_IMAGES
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                permissionRequestCode
            )
        } else {
            loadImagesFromGallery()
        }

    }

    private fun loadImagesFromGallery() {
        // 갤러리에서 이미지 URI 가져오기
        val cursor = requireActivity().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.Media._ID),
            null,
            null,
            MediaStore.Images.Media.DATE_ADDED + " DESC"
        )

        cursor?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon()
                    .appendPath(id.toString()).build().toString()
                imageList.add(contentUri)
            }
        }

        // RecyclerView 설정
        binding.rvPostImg.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvPostImg.adapter = PostImgAdapter(imageList)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionRequestCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadImagesFromGallery()
            }
        }
    }

    private fun openGallery() {
        // 갤러리에서 이미지를 선택하는 Intent 생성
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, pickImageRequest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == pickImageRequest && resultCode == Activity.RESULT_OK) {
            // 이미지가 성공적으로 선택되었을 때
            var selectedImageUri: Uri? = data?.data

            // 선택한 이미지를 ImageView에 설정
            selectedImageUri?.let {
                binding.ivPostImg.setImageURI(it)
                binding.ivPostImg.scaleType = ImageView.ScaleType.CENTER_CROP
                binding.cardView.visibility = VISIBLE

                binding.etPostContent.clearFocus()
                binding.etPostContent.hint = "내용 추가..."

                binding.horizontalScrollView.visibility = GONE

                binding.icDeleteImg.visibility = VISIBLE
                binding.icDeleteImg.bringToFront()

                binding.icDeleteImg.setOnClickListener {
                    // 이미지 삭제 처리
                    selectedImageUri = null
                    binding.icDeleteImg.visibility = GONE
                    binding.ivPostImg.visibility = GONE

                    binding.etPostContent.requestFocus()
                    binding.etPostContent.hint = "무슨 일이 일어나고 있나요?"

                }
            }

            //delete Button 숨기기
            binding.icDeleteImg.visibility = GONE
        }
    }
}
