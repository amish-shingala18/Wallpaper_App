package com.example.wallpaper

import android.Manifest
import android.Manifest.permission.CAMERA
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaper.adapter.WallpaperAdapter
import com.example.wallpaper.databinding.ActivityMainBinding
import com.example.wallpaper.domain.ClientApi.Companion.getApi
import com.example.wallpaper.domain.InternetConnectivity
import com.example.wallpaper.domain.MyApplication
import com.example.wallpaper.interfaces.ApiInterface
import com.example.wallpaper.model.HitsItem
import com.example.wallpaper.model.WallpaperModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
@Suppress("UNCHECKED_CAST", "DEPRECATION")
class MainActivity : AppCompatActivity(),InternetConnectivity.ConnectivityReceiverListener {
    private lateinit var requestMultiplePermission: ActivityResultLauncher<Array<String>>
    private lateinit var requestCameraPermission: ActivityResultLauncher<String>
    private var pageNo: Int=1
    private lateinit var binding : ActivityMainBinding
    private lateinit var wallpaperAdapter: WallpaperAdapter
    var search="wallpaper"
    private var permissionLists = arrayOf(
        CAMERA,
        Manifest.permission.CALL_PHONE,
        Manifest.permission.SEND_SMS)
    companion object {
        var wallpaperList = mutableListOf<HitsItem>()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val internetConnectivity = InternetConnectivity()
        registerReceiver(internetConnectivity, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        requestCameraPermission =registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it){}
        }
        requestMultiplePermission =registerForActivityResult(ActivityResultContracts.
        RequestMultiplePermissions()) {
            for (x in it) { }
        }
        initAdapter()
        scrollPagination()
        searchWallpaper()

        (application as MyApplication).liveData.observe(this@MainActivity) { it ->
            if (it) {
                Log.d(
                    "TAG",
                    "onCreate: ===================Activity Internet is On===================="
                )
                Toast.makeText(this, "Activity Internet is On", Toast.LENGTH_SHORT).show()
            } else {
                Log.e(
                    "TAG",
                    "onCreate: ===================Activity Internet is Off====================",
                )
                Toast.makeText(this, "Activity Internet is Off", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun wallpaperApi(search: String,page:Int){
        val retrofit=getApi().create(ApiInterface::class.java)
        retrofit.getWallpaper(value1 = search, value2 = page).enqueue(object :Callback<WallpaperModel>{
            override fun onResponse(
                call: Call<WallpaperModel>,
                response: Response<WallpaperModel>){
                if (response.isSuccessful) {
                    if (response.body()?.hits!!.isEmpty()) {
                        binding.txtNoResult.visibility = View.VISIBLE
                        wallpaperAdapter.updateList(response.body()!!.hits as MutableList<HitsItem>)
                        initAdapter()
                    } else {
                        binding.txtNoResult.visibility = View.GONE
                        wallpaperAdapter.updateList(response.body()!!.hits as MutableList<HitsItem>)
                        initAdapter()
                    }
                }
            }
            override fun onFailure(call: Call<WallpaperModel>, t: Throwable) {}
        })
    }
    private fun initAdapter(){
        wallpaperAdapter=WallpaperAdapter(wallpaperList)
        binding.rvWallpaper.adapter=wallpaperAdapter
    }
    private fun scrollPagination(){
        binding.rvWallpaper.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val layoutManager=recyclerView.layoutManager as GridLayoutManager
                val firstVisibleItem=layoutManager.findFirstVisibleItemPosition()
                val totalItemCount=layoutManager.itemCount
                val lastVisibleItem=layoutManager.findLastVisibleItemPosition()
                if(firstVisibleItem+lastVisibleItem>=totalItemCount){
                    pageNo++
                    wallpaperApi(search,pageNo)
                }
            }
        })
    }
    private fun searchWallpaper(){
        binding.svWallpaper.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                search = query!!
                wallpaperAdapter.search()
                pageNo=1
                wallpaperApi(search,pageNo)
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (isConnected){
            binding.imgNoNet.visibility=View.GONE
            binding.txtNoNet.visibility=View.GONE
            binding.txtNoNet2.visibility=View.GONE
            binding.rvWallpaper.visibility=View.VISIBLE
            binding.svWallpaper.visibility=View.VISIBLE
            pageNo=1
            wallpaperApi(search,pageNo)
        }
        else{
            binding.imgNoNet.visibility=View.VISIBLE
            binding.txtNoNet.visibility=View.VISIBLE
            binding.txtNoNet2.visibility=View.VISIBLE
            binding.rvWallpaper.visibility=View.GONE
            binding.svWallpaper.visibility=View.GONE
        }
    }
    override fun onResume() {
        super.onResume()
        InternetConnectivity.connectivityReceiverListener = this
    }
    private fun cameraPermission(): Boolean {
        if (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        else{
            requestCameraPermission.launch(CAMERA)
            return false
        }
    }
    private fun multiplePermissions(): Boolean {
        if (permissionLists.size==PackageManager.PERMISSION_GRANTED) {
            return true
        }
        else{
            requestMultiplePermission.launch(permissionLists)
            return false
        }
    }
    private fun checkInternet(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val network = connectivityManager.activeNetwork
        if (network == null) {
            Toast.makeText(this, "Internet not connected", Toast.LENGTH_SHORT).show()
            return false
        } else {
            Toast.makeText(this, "Internet connected", Toast.LENGTH_SHORT).show()
            return true
        }
    }
}