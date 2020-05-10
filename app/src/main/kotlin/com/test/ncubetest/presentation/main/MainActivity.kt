package com.test.ncubetest.presentation.main

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.ncubetest.R
import com.test.ncubetest.presentation.posts.PostsFragment
import com.test.ncubetest.util.network.ConnectionStateReceiver

class MainActivity : AppCompatActivity() {
    lateinit var connectionStateReceiver: ConnectionStateReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerReceiver(
            ConnectionStateReceiver().also { connectionStateReceiver = it },
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        initUI()
    }

    private fun initUI() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PostsFragment())
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(connectionStateReceiver)
    }
}
