package com.example.userlist.ui.main

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.userlist.R
import com.example.userlist.base.BaseFragment
import com.example.userlist.data.api.model.UserInfo
import com.example.userlist.util.loadCircleImage
import com.example.userlist.util.reformatDate
import com.example.userlist.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_user_details.*

class UserDetailsFragment : BaseFragment<MainViewModel>() {

    private val user by lazy {
        arguments?.getParcelable<UserInfo>(KEY_USER)
    }

    companion object {

        private const val RC_CALL_PHONE = 1122

        private const val KEY_USER = "user"

        fun newInstance(user: UserInfo) = UserDetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_USER, user)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user?.also { user ->
            image_user.loadCircleImage(user.picture?.large)
            text_user_name.text = getString(R.string.user_name, user.name?.first, user.name?.last)
            text_user_age.text = getString(R.string.user_gender_age, user.gender, user.dob?.age, user.dob?.date?.reformatDate())
            text_user_phone.text = user.phone
            text_user_phone.setOnClickListener {
                checkPermissions(
                    arrayOf(android.Manifest.permission.CALL_PHONE),
                    RC_CALL_PHONE
                ) { rc, results ->
                    if (rc == RC_CALL_PHONE && results.all { it == PackageManager.PERMISSION_GRANTED }) {
                        startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:${user.phone}")))
                    }
                }
            }
            text_user_email.setText(user.email)
            text_user_skype.setText(
                getString(
                    R.string.user_skype,
                    user.location?.state,
                    user.location?.city
                )
            )
            sectioned_user_data.setSections(user.toSections())
        }
    }

}