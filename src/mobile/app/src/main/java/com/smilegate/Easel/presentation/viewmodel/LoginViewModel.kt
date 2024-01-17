package com.smilegate.Easel.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class LoginViewModel : ViewModel() {
    // LiveData를 사용하여 데이터 관리
    private val _id = MutableLiveData<String>()
    val id: LiveData<String> get() = _id

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password

    // 값을 설정하는 메서드
    fun setIdValue(data: String) {
        _id.value = data
    }

    fun setPasswordValue(data: String) {
        _password.value = data
    }
}