package com.example.myapplication.application

import android.content.Context
import com.example.myapplication.BuildConfig
import com.example.myapplication.data.SampleSharedPreferences
import jp.team.eworks.e_core_library.application.BaseApplication
import jp.team.eworks.e_core_library.utils.MyDebugTree
import timber.log.Timber

class SampleApplication: BaseApplication() {
    companion object {
        private lateinit var pAppContext: Context
        val appContext: Context
            get() = pAppContext
    }

    override fun onCreate() {
        super.onCreate()
        pAppContext = applicationContext

        initSharedPreferences()
        initTimber()
    }

    private fun initSharedPreferences() {
        SampleSharedPreferences.initialize(appContext)
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(MyDebugTree())
        }
    }
}
