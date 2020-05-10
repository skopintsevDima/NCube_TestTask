package com.test.ncubetest.app

import android.app.Application
import com.test.ncubetest.app.di.AppComponent
import com.test.ncubetest.app.di.AppModule
import com.test.ncubetest.app.di.DaggerAppComponent
import com.test.ncubetest.data.db.di.DbModule
import com.test.ncubetest.util.di.UtilModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        buildAppComponent()
    }

    private fun buildAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .dbModule(DbModule())
            .utilModule(UtilModule())
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}