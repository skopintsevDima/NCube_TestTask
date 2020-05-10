package com.test.ncubetest.util.network

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.test.ncubetest.app.App
import javax.inject.Inject

class ConnectionStateReceiver: BroadcastReceiver() {
    @Inject
    lateinit var connectivityChecker: ConnectivityChecker

    init { App.appComponent.inject(this) }

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            if (it.isRelevant()){
                connectivityChecker.refreshConnectionState()
            }
        }
    }

    private fun Intent.isRelevant(): Boolean = when (action) {
        ConnectivityManager.CONNECTIVITY_ACTION -> true
        else -> false
    }
}