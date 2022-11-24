package com.example.carapi.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carapi.repository.login.LoginRepository
import com.example.carapi.util.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    ViewModel() {

    val login = MutableLiveData<Resource<FirebaseUser>?>(null)
    val register = MutableLiveData<Resource<FirebaseUser>?>(null)

    val currentUser: FirebaseUser?
        get() = loginRepository.currentUser

    init {
        if (loginRepository.currentUser != null) {
            login.value = Resource.Success(loginRepository.currentUser!!)
        }
    }


    fun login(email: String, password: String) = viewModelScope.launch {
        login.value = Resource.Loading()
        val result = loginRepository.login(email, password)
        login.value = result

    }

    fun register(email: String, name: String, password: String) = viewModelScope.launch {
        register.value = Resource.Loading()
        val result = loginRepository.register(email, name, password)
        register.value = result
    }

    fun logout() {
        loginRepository.logout()
        login.value = null
        register.value = null
    }
}