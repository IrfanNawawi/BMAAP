package id.heycoding.submissiongithubuser.data.remote.service

import id.heycoding.submissiongithubuser.data.remote.response.DetailUserResponse
import id.heycoding.submissiongithubuser.data.remote.response.User
import id.heycoding.submissiongithubuser.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("search/users")
    fun getSearchUser(
        @Query("q") username: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/following")
    fun getFollowingUser(
        @Path("username") username: String
    ): Call<List<User>>

    @GET("users/{username}/followers")
    fun getFollowersUser(
        @Path("username") username: String
    ): Call<List<User>>
}