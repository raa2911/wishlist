package com.raapp.wishlist.customView

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.raapp.wishlist.R
import kotlinx.android.synthetic.main.profile_list_item.view.*

class ProfileOptionsItem : FrameLayout {

    init {
        FrameLayout.inflate(context, R.layout.profile_list_item, this)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        processAttributeSet(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context, attrs,
        defStyle
    ) {
        processAttributeSet(attrs)
    }

    private fun processAttributeSet(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.ProfileOptionsItem,
            0,
            0
        )
        typedArray.getString(R.styleable.ProfileOptionsItem_android_text)?.also {
            setText(it)
        }
        setIcon(typedArray.getDrawable(R.styleable.ProfileOptionsItem_android_drawable))
        typedArray.recycle()
    }

    fun setText(text: CharSequence) {
        profile_item_text.text = text
    }

    fun setIcon(icon: Drawable?) {
        if (icon == null) profile_item_icon.visibility = View.GONE
        else profile_item_icon.setImageDrawable(icon)
    }
}