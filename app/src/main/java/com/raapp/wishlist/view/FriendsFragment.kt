package com.raapp.wishlist.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.raapp.wishlist.BaseFragment

import com.raapp.wishlist.R
import com.raapp.wishlist.viewModels.EmptyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FriendsFragment : BaseFragment<EmptyViewModel>() {
    override val viewModels: EmptyViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

}
