package com.raapp.wishlist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.raapp.wishlist.BaseFragment
import com.raapp.wishlist.BuildConfig

import com.raapp.wishlist.R
import com.raapp.wishlist.adapters.WishListAdapter
import com.raapp.wishlist.models.Wish
import com.raapp.wishlist.repository.WishRepository
import com.raapp.wishlist.repository.WishRepositoryImpl
import com.raapp.wishlist.repository.WishRepositoryMockImpl
import java.util.*
import kotlin.concurrent.thread
import kotlin.concurrent.timerTask

/**
 * A main [Fragment] screen.
 * Consist list of user wishes and route controls to all flow of application.
 */
class WishListFragment : BaseFragment() {
    private var firebaseUser: FirebaseUser? = null
    private var wishRepository: WishRepository? = null
    private var recycleView: RecyclerView? = null
    private val adapter = WishListAdapter()
    private var timer = Timer()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.context?.also {
            wishRepository = if (BuildConfig.MOCK_DATA) {
                WishRepositoryMockImpl()
            } else {
                WishRepositoryImpl.getInstance(it)
            }
        }
        val view = inflater.inflate(R.layout.fragment_wish_list, container, false)
        view.findViewById<FloatingActionButton>(R.id.wish_list_fab).setOnClickListener {
            this@WishListFragment.findNavController().navigate(R.id.action_wishListFragment_to_wishEditFragment)
        }
        recycleView = view.findViewById<RecyclerView>(R.id.wish_list_recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@WishListFragment.adapter
        }
        updateList()
        return view
    }

    private fun updateList() {
        thread {
            val wishList = wishRepository?.getAllWishesLocal() ?: return@thread
            activity?.runOnUiThread {
                adapter.addItems(wishList)
            }
        }
//        startTimer()
    }

    private fun startTimer() {
        val task = timerTask {
            activity?.runOnUiThread {
                adapter.addItems(
                    listOf(
                        Wish(
                            title = "Some new title",
                            description = "Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text ",
                            privacy = 1
                        )
                    )
                )
            }
        }
        timer.schedule(task, 700, 700)
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

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }
}
