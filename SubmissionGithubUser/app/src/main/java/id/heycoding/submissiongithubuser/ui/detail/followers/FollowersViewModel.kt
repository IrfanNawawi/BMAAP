package id.heycoding.submissiongithubuser.ui.detail.followers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.heycoding.submissiongithubuser.data.remote.response.User
import id.heycoding.submissiongithubuser.data.remote.service.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {

    private val _followersUser = MutableLiveData<List<User>>()
    val followersUser: LiveData<List<User>> = _followersUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FollowersViewModel"
    }

    /**
     * Function to get data followers.
     */
    fun getFollowersUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiServices().getFollowersUser(username)
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followersUser.value = response.body()
                } else {
                    Log.d(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }
}