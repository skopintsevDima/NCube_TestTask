package com.test.ncubetest.util.network

import androidx.lifecycle.LiveData

interface ConnectivityChecker {
    fun getConnectionState(): LiveData<Boolean>

    fun refreshConnectionState()
}
