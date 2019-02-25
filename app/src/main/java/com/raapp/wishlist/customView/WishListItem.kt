package com.raapp.wishlist.customView

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.raapp.wishlist.R
import com.raapp.data.models.PrivacyType
import com.raapp.data.models.Wish
import kotlinx.android.synthetic.main.wish_list_item.view.*

class WishListItem : FrameLayout {
    init {
        RelativeLayout.inflate(context, R.layout.wish_list_item, this)
    }

    var item: Wish? = null
        set(value) {
            field = value
            updateUI()
        }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context, attrs,
        defStyle
    )

    private fun updateUI() {
        item?.let {
            wish_item_title.text = it.title
            with(wish_item_description) {
                if (it.description.isNullOrEmpty()) {
                    text = context.getString(R.string.empty_description)
                    alpha = 0.5f
                } else {
                    text = it.description
                    alpha = 1.0f
                }
            }
            val privacyEnum = PrivacyType.getById(it.privacy)
            wish_item_privacy.text = context.getString(
                when (privacyEnum) {
                    PrivacyType.PRIVATE -> R.string.privacy_type_private
                    PrivacyType.FRIENDS -> R.string.privacy_type_friend
                    PrivacyType.PUBLIC -> R.string.privacy_type_public
                }
            )
        }
    }
}