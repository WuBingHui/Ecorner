package com.csnt.android_sport.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.Flowable

/**
 * Converts LiveData into a Flowable
 */
fun <T> LiveData<T>.toFlowable(lifecycleOwner: LifecycleOwner) : Flowable<T> =
    Flowable.fromPublisher(LiveDataReactiveStreams.toPublisher(lifecycleOwner, this))


/**
 * Converts a Flowable into LiveData
 */
fun <T> Flowable<T>.toLiveData(): LiveData<T> = LiveDataReactiveStreams.fromPublisher(this)