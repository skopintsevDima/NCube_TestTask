package com.test.ncubetest.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.ncubetest.R
import com.test.ncubetest.presentation.posts.PostsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
    }

    private fun initUI() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PostsFragment())
            .commit()
    }
}
