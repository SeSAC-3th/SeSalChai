package org.sesac.management.util.common

import android.app.Application

class ApplicationClass : Application() {
    companion object {
        private lateinit var appInstance: ApplicationClass
        fun getApplicationContext() : ApplicationClass = appInstance
    }
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }
}