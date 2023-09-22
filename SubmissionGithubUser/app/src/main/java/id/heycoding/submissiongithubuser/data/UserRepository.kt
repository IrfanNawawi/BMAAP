package id.heycoding.submissiongithubuser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import id.heycoding.submissiongithubuser.data.local.dao.GithubUserDao
import id.heycoding.submissiongithubuser.data.local.entity.UserEntity
import id.heycoding.submissiongithubuser.data.remote.response.UserResponse
import id.heycoding.submissiongithubuser.data.remote.service.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor(
    private val apiServices: ApiServices,
    private val githubUserDao: GithubUserDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<UserEntity>>>()

    fun getSearchUser(username: String): LiveData<Result<List<UserEntity>>> {
        result.value = Result.Loading
        val client = apiServices.getSearchUser(username)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val users = response.body()?.items
                    val userList = ArrayList<UserEntity>()
                    appExecutors.diskIO.execute {
                        users?.forEach { user ->
                            val isFavorited = githubUserDao.isUserFavorited(user.login)
                            val githubUser = UserEntity(
                                user.id,
                                user.login,
                                user.avatarUrl,
                                user.following,
                                user.followers,
                                isFavorited
                            )
                            userList.add(githubUser)
                        }
                        githubUserDao.deleteAll()
                        githubUserDao.insert(userList)

                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })
        val localData = githubUserDao.getAllUser()
        result.addSource(localData) { newData: List<UserEntity> ->
            result.value = Result.Success(newData)
        }
        return result
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiServices: ApiServices,
            githubUserDao: GithubUserDao,
            appExecutors: AppExecutors
        ): UserRepository = instance ?: synchronized(this) {
            instance ?: UserRepository(apiServices, githubUserDao, appExecutors)
        }.also { instance = it }
    }
}