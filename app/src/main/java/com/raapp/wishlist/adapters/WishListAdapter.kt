package com.raapp.wishlist.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raapp.wishlist.customView.WishListItem
import com.raapp.data.models.Wish

class WishListAdapter : RecyclerView.Adapter<WishListAdapter.MyViewHolder>() {
    var wishList: MutableList<Wish> = mutableListOf()

    fun addItems(newWishes: List<Wish>) {
        wishList.addAll(newWishes)
        notifyItemRangeInserted(wishList.size - newWishes.size, newWishes.size)
    }

    fun removeItem(item: Wish) {
        val index = wishList.indexOf(item)
        if (index > -1 && index < wishList.size) {
            wishList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val wishItemView = WishListItem(parent.context)
        return MyViewHolder(wishItemView)
    }

    override fun getItemCount(): Int {
        return wishList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        (holder.itemView as WishListItem).item = wishList[position]
    }

    class MyViewHolder(itemView: WishListItem) : RecyclerView.ViewHolder(itemView)
}