package jp.team.eworks.e_core_library.application

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import jp.team.eworks.e_core_library.BuildConfig
import jp.team.eworks.e_core_library.utils.MyDebugTree
import timber.log.Timber

abstract class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.addObserver(ApplicationLifecycle())

        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(MyDebugTree())
        }
    }

    open fun onCreateFromLifecycle() {}
    open fun onStartFromLifecycle() {}
    open fun onResumeFromLifecycle() {}
    open fun onPauseFromLifecycle() {}
    open fun onStopFromLifecycle() {}
    open fun onDestroyFromLifecycle() {}

    @Suppress("unused")
    private inner class ApplicationLifecycle: LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun onCreate() {
            onCreateFromLifecycle()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onStart() {
            onStartFromLifecycle()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onResume() {
            onResumeFromLifecycle()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun onPause() {
            onPauseFromLifecycle()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onStop() {
            onStopFromLifecycle()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            onDestroyFromLifecycle()
        }
    }
}
