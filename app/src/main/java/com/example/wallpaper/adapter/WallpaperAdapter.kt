package com.example.wallpaper.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaper.R
import com.example.wallpaper.activity.WallpaperActivity
import com.example.wallpaper.databinding.WallpaperSampleBinding
import com.example.wallpaper.model.HitsItem

class WallpaperAdapter(private val wallpaperList: MutableList<HitsItem>) : RecyclerView.Adapter<WallpaperAdapter.WallpaperViewHolder>() {
    class WallpaperViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding=WallpaperSampleBinding.bind(itemView)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(l1: MutableList<HitsItem>){
        wallpaperList.addAll(l1)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        val view=LayoutInflater.from(parent.context).
        inflate(R.layout.wallpaper_sample,parent,false)
        return WallpaperViewHolder(view)
    }
    override fun getItemCount(): Int {
        return wallpaperList.size
    }
    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(wallpaperList[position].previewURL).placeholder(R.drawable.walllpaper_logo)
            .into(holder.binding.imgWallpaper)
        holder.binding.cvWallpaper.setOnClickListener {
            val intent = Intent(holder.itemView.context, WallpaperActivity::class.java)
            intent.putExtra("imagePosition", position)
            holder.itemView.context.startActivity(intent)
        }
    }
    fun search() {
        wallpaperList.clear()
    }
}