package com.raapp.wishlist.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController

import com.raapp.wishlist.R
import androidx.navigation.ui.NavigationUI
import com.raapp.wishlist.BaseFragment
import com.raapp.wishlist.viewModels.EmptyViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 *
 */
class MainFragment : BaseFragment<EmptyViewModel>() {
    override val viewModels: EmptyViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onStart() {
        super.onStart()
        activity?.findNavController(R.id.navigation_host_fragment_main)?.also {
            NavigationUI.setupWithNavController(main_bottom_navigation, it)
        }
    }
}
