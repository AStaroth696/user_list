package com.example.userlist.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userlist.R
import com.example.userlist.adapter.UsersAdapter
import com.example.userlist.base.BaseFragment
import com.example.userlist.util.Result
import com.example.userlist.util.toast
import com.example.userlist.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_user_list.*

class UserListFragment : BaseFragment<MainViewModel>() {

    companion object {

        fun newInstance() = UserListFragment()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usersAdapter = UsersAdapter {
            viewModel.goToDetails.callWithValue(it)
        }
        recycler_user_list.adapter = usersAdapter
        recycler_user_list.layoutManager = LinearLayoutManager(context)
        recycler_user_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (usersAdapter.itemCount > 0
                    && !recyclerView.canScrollVertically(1)
                ) {
                    viewModel.requestUsers()
                }
            }

        })

        swipe_refresh_user_list.setOnRefreshListener {
            viewModel.refreshUsers()
        }

        viewModel.usersData.subscribe {
            when (it.status) {
                Result.Status.LOADING -> {
                    viewModel.loading.callWithValue(true)
                    swipe_refresh_user_list.isRefreshing = false
                }
                Result.Status.ERROR -> {
                    viewModel.loading.callWithValue(false)
                    swipe_refresh_user_list.isRefreshing = false
                    context?.toast(it.error?.message ?: "")
                }
                Result.Status.SUCCESS -> {
                    viewModel.loading.callWithValue(false)
                    swipe_refresh_user_list.isRefreshing = false
                    text_empty_list.isVisible = it.data.isEmpty()
                    usersAdapter.submitList(it.data)
                }
            }
        }

        if (viewModel.getUsers().isEmpty()) {
            viewModel.requestUsers()
        } else {
            text_empty_list.isVisible = false
            usersAdapter.submitList(viewModel.getUsers())
        }
    }

}