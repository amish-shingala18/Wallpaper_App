package com.example.wallpaper.adapter

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.wallpaper.R
import com.example.wallpaper.databinding.SetwallpaperSampleBinding
import com.example.wallpaper.model.HitsItem
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Suppress("DEPRECATION")
class SetWallpaperAdapter(private var list: List<HitsItem>):
    RecyclerView.Adapter<SetWallpaperAdapter.SetWallpaperViewHolder>() {
    class SetWallpaperViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = SetwallpaperSampleBinding.bind(itemView)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetWallpaperViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.setwallpaper_sample, parent, false)
        return SetWallpaperViewHolder(view)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: SetWallpaperViewHolder, position: Int) {
        val imageUrl = list[position].largeImageURL
        Glide.with(holder.itemView.context)
            .load(imageUrl).placeholder(R.drawable.walllpaper_logo)
            .into(holder.binding.imageView)
        holder.binding.btnSetWallpaper.setOnClickListener {
            val wallpaperManager = WallpaperManager.getInstance(holder.itemView.context)
            Glide.with(holder.itemView.context).asBitmap().load(imageUrl).into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    wallpaperManager.setBitmap(resource)
                    Toast.makeText(
                        holder.itemView.context,
                        "Changes applied to Home and Lock Screen",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        }
        fun convertImage(): Bitmap {
            holder.binding.imageView.isDrawingCacheEnabled = true
            val bitmap = Bitmap.createBitmap(holder.binding.imageView.drawingCache)
            holder.binding.imageView.isDrawingCacheEnabled = false
            return bitmap
        }
        holder.binding.btnSave.setOnClickListener {
            Log.d("TAG", "onBindViewHolder: ========================Save============================")
            val bitmap = convertImage()
            val sdf = SimpleDateFormat("hh_mm_ss_dd_MM_yyyy", Locale.getDefault())
            val date = Date()
            val image = sdf.format(date)
            val filename =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val path = filename.path + "/" + image + ".png"
            val file = File(path)
            try {
                val fileOutputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                fileOutputStream.close()
                Toast.makeText(
                    holder.itemView.context,
                    "Image saved to gallery",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("TAG", "onBindViewHolder: ================${file.path} ")
            } catch (_: Exception) {

            }
            holder.binding.btnShare.setOnClickListener {
                val shareBitmap = convertImage()
                val bytes = ByteArrayOutputStream()
                shareBitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)
                val sharePath =
                    MediaStore.Images.Media.insertImage(holder.itemView.context.contentResolver,
                        shareBitmap, "title", null)
                val share = Intent(Intent.ACTION_SEND)
                share.setType("image/png")
                val shareImageUri = Uri.parse(sharePath)
                share.putExtra(Intent.EXTRA_STREAM, shareImageUri)
                holder.itemView.context.startActivity(Intent.createChooser(share, "select"))
            }
        }
    }
}