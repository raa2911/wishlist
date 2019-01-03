package com.raapp.wishlist.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.raapp.wishlist.BaseFragment

import com.raapp.wishlist.R
import com.raapp.wishlist.repository.WishRepository
import com.raapp.wishlist.repository.WishRepositoryImpl
import kotlin.concurrent.thread

/**
 * A main [Fragment] screen.
 * Consist list of user wishes and route controls to all flow of application.
 */
class WishListFragment : BaseFragment() {
    private var firebaseUser: FirebaseUser? = null
    private var wishRepository: WishRepository? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.context?.also {
            wishRepository = WishRepositoryImpl.getInstance(it)
        }
        val view = inflater.inflate(R.layout.fragment_wish_list, container, false)
        view.findViewById<FloatingActionButton>(R.id.wish_list_fab).setOnClickListener {
            this@WishListFragment.findNavController().navigate(R.id.action_wishListFragment_to_wishEditFragment)
        }
        updateCounter(view)
        return view
    }

    private fun updateCounter(view: View) {
        thread {
            val wishCount = wishRepository?.getAllWishesLocal()?.size?.toString()
            activity?.runOnUiThread {
                view.findViewById<TextView>(R.id.wish_list_label).text = wishCount
            }
        }
    }

    override fun onStart() {
        super.onStart()
        initToolbar()
    }

    private fun initToolbar() {
        getToolbar()?.inflateMenu(R.menu.wish_list_menu)
        getToolbar()?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.log_out_menu_item -> {
                    context?.let { context ->
                        AuthUI.getInstance()
                            .signOut(context)
                            .addOnCompleteListener {
                                this.findNavController().navigate(R.id.action_wishListFragment_to_splashFragment)
                            }
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseUser = FirebaseAuth.getInstance().currentUser
    }


    override fun onDetach() {
        super.onDetach()
        firebaseUser = null
    }
}
