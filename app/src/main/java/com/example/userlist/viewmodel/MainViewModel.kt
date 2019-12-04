package com.example.userlist.viewmodel

import com.example.userlist.base.BaseViewModel
import com.example.userlist.data.api.model.UserInfo
import com.example.userlist.manager.MainDataManager
import com.example.userlist.util.AsyncDiffUtil
import com.example.userlist.util.SingleLiveEvent

class MainViewModel (private val mainDataManager: MainDataManager) : BaseViewModel() {

    //Navigation
    val goToList = SingleLiveEvent<Unit>()
    val goToDetails = SingleLiveEvent<UserInfo>()

    val loading = SingleLiveEvent<Boolean>()

    val usersData = mainDataManager.usersData

    fun getUsers() = mainDataManager.users

    fun requestUsers() = mainDataManager.requestUsers()

    fun refreshUsers() = mainDataManager.refreshUsers()

    override fun onCleared() {
        mainDataManager.cancelJob()
        AsyncDiffUtil.cancel()
        super.onCleared()
    }

}