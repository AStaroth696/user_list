package com.example.userlist.util

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val mPending = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T?>) {
        super.observe(owner, Observer {
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }

    @MainThread
    override fun setValue(value: T?) {
        mPending.set(true)
        super.setValue(value)
    }

    override fun postValue(value: T?) {
        mPending.set(true)
        super.postValue(value)
    }

    @MainThread
    fun call() {
        value = null
    }

    @MainThread
    fun callWithValue(data: T?) {
        value = data
    }

    fun postCall(data: T?) {
        postValue(data)
    }

}