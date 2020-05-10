package com.test.ncubetest.util.network

import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ConnectivityCheckerImpl(
    private val connectivityManager: ConnectivityManager
): ConnectivityChecker {
    private val liveConnectionState
            = MutableLiveData<Boolean>().apply { value = isNetworkAvailable() }

    override fun getConnectionState(): LiveData<Boolean> = liveConnectionState

    override fun refreshConnectionState() {
        liveConnectionState.value = isNetworkAvailable()
    }

    private fun isNetworkAvailable(): Boolean
            = connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
}