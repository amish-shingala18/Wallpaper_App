package com.example.wallpaper.domain

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientApi {
    companion object {
        private var BASEURL="https://pixabay.com/"
        private var retrofit: Retrofit? = null
        fun getApi(): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder().baseUrl(BASEURL)
                    .addConverterFactory(GsonConverterFactory.create()).build()
            }
            return retrofit!!
        }
    }
}
