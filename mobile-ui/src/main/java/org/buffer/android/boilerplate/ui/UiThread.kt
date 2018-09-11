package org.buffer.android.boilerplate.ui

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import org.buffer.android.boilerplate.data.executor.PostExecutionThread

/**
 * MainThread (UI Thread) implementation based on a [Scheduler]
 * which will execute actions on the Android UI thread
 */
class UiThread : PostExecutionThread {

    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()

}