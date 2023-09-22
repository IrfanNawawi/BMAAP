package id.heycoding.submissiongithubuser.data.remote.service

import id.heycoding.submissiongithubuser.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiConfig {
    companion object {
        fun getApiServices(): ApiServices {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(
                    Interceptor { chain ->
                        val request: Request = chain.request().newBuilder()
                            .addHeader("Authorization", "token ${BuildConfig.GITHUB_TOKEN}").build()
                        chain.proceed(request)
                    }).build()

            val retrofit =
                Retrofit.Builder().baseUrl(BuildConfig.GITHUB_URL_DEV)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client).build()

            return retrofit.create(ApiServices::class.java)
        }
    }
}