package com.example.userlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.userlist.R
import com.example.userlist.data.api.model.UserInfo
import com.example.userlist.util.AsyncDiffUtil
import com.example.userlist.util.UsersDiffCallback
import com.example.userlist.util.loadCircleImage
import kotlinx.android.synthetic.main.item_user.view.*

class UsersAdapter(
    private var users: MutableList<UserInfo> = mutableListOf(),
    private val onUserClicked: (UserInfo) -> Unit
) :
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        if (position in users.indices) {
            holder.bind(users[position])
        }
    }

    fun submitList(list: MutableList<UserInfo>) {
        if (users.isEmpty()) {
            users = list
            notifyDataSetChanged()
        } else {
            val callback = UsersDiffCallback(users, list)
            AsyncDiffUtil.calculateDiffAsync(callback) {
                users = list
                it.dispatchUpdatesTo(this)
            }
        }
    }

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(user: UserInfo) {
            itemView.setOnClickListener {
                onUserClicked(user)
            }
            itemView.image_user_circle.loadCircleImage(user.picture?.thumbnail)
            itemView.text_user_name.text =
                "${user.name?.first} ${user.name?.last}"
        }

    }

}