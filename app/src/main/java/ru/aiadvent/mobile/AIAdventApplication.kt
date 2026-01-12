package ru.aiadvent.mobile

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.aiadvent.mobile.di.appModules

class AIAdventApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@AIAdventApplication)
            modules(appModules)
        }
    }
}
