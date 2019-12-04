package com.example.userlist.util

import androidx.recyclerview.widget.DiffUtil
import kotlinx.coroutines.*

object AsyncDiffUtil {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    fun calculateDiffAsync(
        callback: DiffUtil.Callback,
        onCalculated: (DiffUtil.DiffResult) -> Unit
    ) {
        scope.launch(Dispatchers.IO) {
            val result = DiffUtil.calculateDiff(callback)
            withContext(Dispatchers.Main) {
                onCalculated(result)
            }
        }
    }

    fun cancel() {
        job.cancelChildren()
    }

}