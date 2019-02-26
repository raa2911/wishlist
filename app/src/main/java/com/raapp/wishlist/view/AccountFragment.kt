package com.raapp.wishlist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.auth.AuthUI

import com.raapp.wishlist.R
import com.raapp.wishlist.extentions.findMainNavController
import kotlinx.android.synthetic.main.fragment_account.view.*

class AccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        view.profile_log_out.setOnClickListener { logOut() }
        return view
    }

    private fun logOut() {
        context?.also { context ->
            AuthUI.getInstance()
                .signOut(context)
                .addOnCompleteListener {
                    activity?.findMainNavController()?.navigate(R.id.action_mainFragment_to_splashFragment)
                }
        }
    }
}
