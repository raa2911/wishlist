package com.raapp.wishlist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.raapp.wishlist.BaseFragment
import com.raapp.wishlist.R
import com.raapp.wishlist.adapters.WishListAdapter
import com.raapp.wishlist.viewModels.WishListViewModel
import kotlinx.android.synthetic.main.fragment_wish_list.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

/**
 * A main [Fragment] screen.
 * Consist list of user wishes and route controls to all flow of application.
 */
class WishListFragment : BaseFragment<WishListViewModel>() {
    private var firebaseUser: FirebaseUser? = null
    private var recycleView: RecyclerView? = null
    private val adapter = WishListAdapter()
    private var timer = Timer()
    override val viewModels: WishListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_wish_list, container, false)
        view.wish_list_fab.setOnClickListener { createNewWishScreen() }
        recycleView = view.wish_list_recycler_view.apply {
            adapter = this@WishListFragment.adapter
        }
        updateList()
        return view
    }

    private fun createNewWishScreen() {
        activity?.also {
            Navigation
                .findNavController(it, R.id.nav_host_fragment)
                .navigate(R.id.action_mainFragment_to_wishEditFragment)
        }
    }

    private fun updateList() {
        viewModels.getWishList().observe(this, androidx.lifecycle.Observer {
            adapter.addItems(it)
        })
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
