package id.heycoding.submissiongithubuser.data.remote

import id.heycoding.submissiongithubuser.BuildConfig
import id.heycoding.submissiongithubuser.data.response.DetailUserResponse
import id.heycoding.submissiongithubuser.data.response.User
import id.heycoding.submissiongithubuser.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUser(
        @Query("q") username: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowingUser(
        @Path("username") username: String
    ): Call<List<User>>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowersUser(
        @Path("username") username: String
    ): Call<List<User>>
}