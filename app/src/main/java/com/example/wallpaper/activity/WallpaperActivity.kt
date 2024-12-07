package com.example.wallpaper.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wallpaper.MainActivity
import com.example.wallpaper.R
import com.example.wallpaper.adapter.SetWallpaperAdapter
import com.example.wallpaper.databinding.ActivityWallpaperBinding

class WallpaperActivity : AppCompatActivity(){
    private lateinit var binding: ActivityWallpaperBinding
    private lateinit var setWallpaperAdapter: SetWallpaperAdapter
        override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
            binding=ActivityWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getImage()
    }
    private fun getImage() {
        val imagePosition = intent.getIntExtra("imagePosition",1)
        setWallpaperAdapter = SetWallpaperAdapter(MainActivity.wallpaperList)
        binding.vpWallpaper.adapter = setWallpaperAdapter
        binding.vpWallpaper.setCurrentItem(imagePosition,false)
    }
}