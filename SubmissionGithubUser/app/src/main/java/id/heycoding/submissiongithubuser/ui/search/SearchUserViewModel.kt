package id.heycoding.submissiongithubuser.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.heycoding.submissiongithubuser.data.UserRepository
import id.heycoding.submissiongithubuser.data.local.entity.UserEntity
import id.heycoding.submissiongithubuser.data.remote.response.User
import id.heycoding.submissiongithubuser.data.remote.response.UserResponse
import id.heycoding.submissiongithubuser.data.remote.service.ApiConfig
import id.heycoding.submissiongithubuser.util.DataDummy
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchUserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _user = MutableLiveData<List<User>>()
    val user: LiveData<List<User>> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    /**
     * Function to get search user.
     */
    fun searchUser(username: String) = userRepository.getSearchUser(username)

    /**
     * Function to testing data user.
     */
    fun getUser(): List<UserEntity> = DataDummy.generateDummyUser()
}