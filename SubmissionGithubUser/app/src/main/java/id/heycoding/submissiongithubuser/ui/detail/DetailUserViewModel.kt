package id.heycoding.submissiongithubuser.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.heycoding.submissiongithubuser.data.local.entity.UserEntity
import id.heycoding.submissiongithubuser.data.remote.response.DetailUserResponse
import id.heycoding.submissiongithubuser.data.remote.service.ApiConfig
import id.heycoding.submissiongithubuser.domain.repository.GithubUserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : ViewModel() {

    private val mGithubUserRepository: GithubUserRepository = GithubUserRepository(application)

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "DetailUserViewModel"
    }

    /**
     * Function to get data detail user.
     */
    fun getDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiServices().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.d(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }

    /**
     * Function to check favorite user by id.
     */
    fun checkFavoriteUserById(id: Int): LiveData<UserEntity> =
        mGithubUserRepository.getFavoriteUserById(id)

    /**
     * Function to insert favorite user.
     */
    fun insertFavoriteUser(userEntity: UserEntity) {
        mGithubUserRepository.insert(userEntity)
    }

    /**
     * Function to delete favorite user.
     */
    fun deleteFavoriteUser(id: Int) {
        mGithubUserRepository.delete(id)
    }
}