package com.harnet.dogbreeds.util

import android.os.Handler
import android.os.Looper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentBindingProvider<out T : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) : ReadOnlyProperty<Fragment, T>, LifecycleObserver {

    private var binding: T? = null

    private var thisRef: Fragment? = null


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        this.thisRef?.viewLifecycleOwner?.lifecycle?.removeObserver(this)

        // Fragment.viewLifecycleOwner call LifecycleObserver.onDestroy() before Fragment.onDestroyView().
        // That's why we need to postpone reset of the viewBinding
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post {
            binding = null
        }
    }

    override operator fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        this.thisRef = thisRef
        thisRef.viewLifecycleOwner.lifecycle.addObserver(this)

        return binding ?: createBinding(thisRef).also { binding = it }
    }


    private fun createBinding(fragment: Fragment): T {
        return DataBindingUtil.inflate(fragment.layoutInflater, layoutRes, null, false)
    }

}
