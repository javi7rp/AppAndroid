package com.example.hospedajetema3.retrofit

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val URL_BASE_RETROFIT = "http://10.0.2.2/api-juegos/"

    private fun provideGson(): GsonConverterFactory {
        val gson = GsonBuilder().setLenient().create()
        return GsonConverterFactory.create(gson)
    }

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(URL_BASE_RETROFIT)
            .addConverterFactory(provideGson())
            .build()
            .create(ApiService::class.java)
    }
}