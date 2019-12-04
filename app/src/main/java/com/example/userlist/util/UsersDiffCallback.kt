package com.example.userlist.util

import androidx.recyclerview.widget.DiffUtil
import com.example.userlist.data.api.model.UserInfo

class UsersDiffCallback (
    private val oldList: MutableList<UserInfo>,
    private val newList: MutableList<UserInfo>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] === newList[newItemPosition]

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

}