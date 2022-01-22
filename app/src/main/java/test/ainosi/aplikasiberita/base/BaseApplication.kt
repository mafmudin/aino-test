package test.ainosi.aplikasiberita.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import test.ainosi.aplikasiberita.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}