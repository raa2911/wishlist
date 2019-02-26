package com.raapp.wishlist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raapp.wishlist.BaseFragment
import com.raapp.wishlist.R
import com.raapp.wishlist.adapters.WishListAdapter
import com.raapp.wishlist.extentions.findMainNavController
import com.raapp.wishlist.viewModels.WishListViewModel
import kotlinx.android.synthetic.main.fragment_wish_list.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

/**
 * A main [Fragment] screen.
 * Consist list of user wishes and route controls to all flow of application.
 */
class WishListFragment : BaseFragment<WishListViewModel>() {
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
        activity?.findMainNavController()?.navigate(R.id.action_mainFragment_to_wishEditFragment)
    }

    private fun updateList() {
        viewModels.getWishList().observe(this, androidx.lifecycle.Observer {
            adapter.addItems(it)
        })
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }
}
