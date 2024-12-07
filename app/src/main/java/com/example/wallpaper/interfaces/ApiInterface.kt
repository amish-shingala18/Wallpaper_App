package com.example.wallpaper.interfaces

import com.example.wallpaper.model.WallpaperModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("api/")
    fun getWallpaper(
        @Query("key")value:String="45834316-418252c3687149c5f905c00c0",
        @Query("q")value1:String,
        @Query("page")value2:Int,
        @Query("orientation")value3:String="vertical",
        @Query("per_page")value4:Int=21
    ): Call<WallpaperModel>

}