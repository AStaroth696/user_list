package com.example.userlist.base

import com.example.userlist.util.checkNull
import com.example.userlist.util.log
import kotlinx.coroutines.*
import retrofit2.Call
import kotlin.coroutines.CoroutineContext

abstract class BaseDataManager {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    protected fun <T> executeAsync(
        block: suspend CoroutineContext.() -> T,
        onSuccess: ((T) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                val res = withContext(Dispatchers.IO) { block(coroutineContext) }
                onSuccess?.invoke(res)
            } catch (e: Exception) {
                onError?.invoke(e)
            }
        }
    }

    protected fun <T> Call<T>.executeRequestAsync(
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        executeAsync({
            executeRequest()
        }, {
            it.checkNull({
                onSuccess(this)
            }, {
                onError(IllegalArgumentException("Response body is null: ${request().url()}"))
            })
        }, {
            onError(it)
        })
    }

    private fun <T> Call<T>.executeRequest(): T? {
        val response = execute()
        return if (response.isSuccessful) {
            response.body()
        } else {
            log("Error executing request: ${response.errorBody()?.string()}")
            null
        }
    }

    protected fun cancel() = job.cancelChildren()

}