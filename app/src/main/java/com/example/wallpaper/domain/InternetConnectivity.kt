package com.example.wallpaper.domain

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

@Suppress("DEPRECATION")
open class InternetConnectivity : BroadcastReceiver() {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        if (connectivityReceiverListener != null) {
            connectivityReceiverListener!!.
            onNetworkConnectionChanged(isConnectedOrConnecting(context!!))
        }
    }
    private fun isConnectedOrConnecting(context: Context): Boolean {
        val connectionManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectionManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }
    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }
    companion object {
        var connectivityReceiverListener: ConnectivityReceiverListener? = null
    }
}