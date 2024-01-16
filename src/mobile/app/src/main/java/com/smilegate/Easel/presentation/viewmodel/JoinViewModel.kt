package com.smilegate.Easel.presentation.viewmodel

import androidx.lifecycle.ViewModel

class JoinViewModel : ViewModel() {

    // 공유할 데이터 변수
    var email: String? = null
    var password: String? = null

    // 값을 설정하는 메서드
    fun setEmailValue(data: String) {
        email = data
    }
    fun setPasswordValue(data: String) {
        password = data
    }

    // 값을 가져오는 메서드
    fun getEmailValue(): String? {
        return email
    }
    fun getPasswordValue(): String? {
        return password
    }
}
