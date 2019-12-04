package com.example.userlist.ui.main

import android.os.Bundle
import androidx.core.view.isVisible
import com.example.userlist.R
import com.example.userlist.base.BaseActivity
import com.example.userlist.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel>() {

    override val fragmentContainerId = R.id.frame_main_fragment_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.loading.subscribe {
            frame_loading.isVisible = it
        }

        viewModel.goToList.subscribe {
            replaceFragment(UserListFragment.newInstance(), false)
        }

        viewModel.goToDetails.subscribe {
            replaceFragment(UserDetailsFragment.newInstance(it))
        }

        viewModel.goToList.call()

    }

}
