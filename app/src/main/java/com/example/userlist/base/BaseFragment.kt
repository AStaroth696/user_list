package com.example.userlist.base

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.koin.android.ext.android.getKoin
import java.lang.reflect.ParameterizedType
import kotlin.jvm.internal.Reflection

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    protected lateinit var viewModel: VM
    private var permissionsCallback: ((Int, IntArray) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        javaClass.genericSuperclass?.also { type ->
            activity?.viewModelStore?.also { vmStore ->
                viewModel = ViewModelProvider(vmStore, object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        val vmClass = Reflection.createKotlinClass(modelClass)
                        return getKoin().get(
                            vmClass, null, null
                        )
                    }
                })
                    .get((type as ParameterizedType).actualTypeArguments[0] as Class<VM>)
            }
        }
        super.onCreate(savedInstanceState)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsCallback?.invoke(requestCode, grantResults)
        permissionsCallback = null
    }

    protected fun <T> LiveData<T>.subscribe(block: (T) -> Unit) {
        observe(viewLifecycleOwner, Observer(block))
    }

    protected fun checkPermissions(permissions: Array<String>, requestCode: Int, callback: (Int, IntArray) -> Unit) {
        context?.also { context ->
            if (permissions.all { ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED }) {
                requestPermissions(permissions, requestCode)
                callback(requestCode, intArrayOf(PackageManager.PERMISSION_GRANTED))
            } else {
                permissionsCallback = callback
                requestPermissions(permissions, requestCode)
            }
        }

    }

}