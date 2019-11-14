package com.csnt.android_sport.main.base

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import android.os.Bundle
import androidx.annotation.Nullable


abstract class  BaseFragment : Fragment() {

    protected var compositeDisposable = CompositeDisposable()
    protected var isViewInitiated: Boolean = false
    protected var isDataInitiated: Boolean = false
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        prepareFetchData()
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewInitiated = true
        prepareFetchData()
    }

    abstract fun getData()

  private  fun prepareFetchData(): Boolean {
        return prepareFetchData(false)
    }

   private fun prepareFetchData(forceUpdate: Boolean): Boolean {
        if (userVisibleHint && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            getData()
            isDataInitiated = true
            return true
        }
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}