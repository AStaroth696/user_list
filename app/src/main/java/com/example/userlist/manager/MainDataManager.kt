package com.example.userlist.manager

import com.example.userlist.base.BaseDataManager
import com.example.userlist.data.api.UserApiService
import com.example.userlist.data.api.model.UserInfo
import com.example.userlist.util.Result
import com.example.userlist.util.SingleLiveEvent
import com.example.userlist.util.USERS_PER_PAGE

interface MainDataManager {

    val users: MutableList<UserInfo>
    val usersData: SingleLiveEvent<Result<MutableList<UserInfo>>>

    fun requestUsers()
    fun refreshUsers()
    fun cancelJob()

}

class MainDataManagerImpl(private val userApiService: UserApiService) : BaseDataManager(),
    MainDataManager {

    override val users = mutableListOf<UserInfo>()
    override val usersData = SingleLiveEvent<Result<MutableList<UserInfo>>>()

    override fun requestUsers() {
        if (usersData.value == null || usersData.value?.status != Result.Status.LOADING) {
            usersData.callWithValue(Result.loading())
            userApiService.getUserList(users.size / USERS_PER_PAGE + 1, USERS_PER_PAGE)
                .executeRequestAsync({
                    users.addAll(it.results)
                    usersData.callWithValue(Result.success(mutableListOf<UserInfo>().apply {
                        addAll(users)
                    }))
                }, {
                    usersData.callWithValue(Result.error(it))
                })
        }
    }

    override fun refreshUsers() {
        users.clear()
        requestUsers()
    }

    override fun cancelJob() {
        cancel()
    }

}