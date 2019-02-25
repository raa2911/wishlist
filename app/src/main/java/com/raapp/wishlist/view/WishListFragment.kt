package com.raapp.wishlist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.raapp.wishlist.BaseFragment
import com.raapp.wishlist.R
import com.raapp.wishlist.adapters.WishListAdapter
import com.raapp.data.repository.WishRepository
import com.raapp.data.repository.implementation.WishRepositoryImpl
import java.util.*
import kotlin.concurrent.thread

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
            wishRepository = WishRepositoryImpl.getInstance(it) //TODO

        }
        val view = inflater.inflate(R.layout.fragment_wish_list, container, false)
        view.findViewById<FloatingActionButton>(R.id.wish_list_fab).setOnClickListener {
            this@WishListFragment.findNavController().navigate(R.id.action_mainFragment_to_wishEditFragment)
        }
        recycleView = view.findViewById<RecyclerView>(R.id.wish_list_recycler_view).apply {
            adapter = this@WishListFragment.adapter
        }
        updateList()
        return view
    }

    private fun updateList() {
        thread {
            val wishList = wishRepository?.getAllWishesLocal() ?: return@thread
//            activity?.runOnUiThread {
//                adapter.addItems(wishList)
//            }
        }
    }


/*    private fun initToolbar() {
        getToolbar()?.inflateMenu(R.menu.wish_list_menu)
        getToolbar()?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.log_out_menu_item -> {
                    context?.let { context ->
                        AuthUI.getInstance()
                            .signOut(context)
                            .addOnCompleteListener {
                                this.findNavController().navigate(R.id.action_mainFragment_to_splashFragment)
                            }
                    }
                    true
                }
                else -> false
            }
        }
    }*/

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
