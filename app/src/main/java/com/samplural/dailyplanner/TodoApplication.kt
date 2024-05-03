package com.samplural.dailyplanner

import android.app.Application
import com.samplural.dailyplanner.data.AppContainer
import com.samplural.dailyplanner.data.AppDataContainer

class TodoApplication : Application() {

    lateinit var container: AppContainer

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}