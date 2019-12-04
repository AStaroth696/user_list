package com.example.userlist.util

data class Result<T>(
    val status: Status,
    val error: Throwable? = null,
    private val dataInitializer: (() -> T)? = null
) {

    val data by lazy {
        dataInitializer!!()
    }

    companion object {

        fun <T> loading() = Result<T>(
            Status.LOADING
        )

        fun <T> success(data: T) = Result<T>(
            Status.SUCCESS,
            dataInitializer = { data })

        fun <T> error(error: Throwable) = Result<T>(
            Status.ERROR,
            error
        )
    }

    enum class Status {
        LOADING,
        SUCCESS,
        ERROR
    }

}