package com.raapp.wishlist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.annotation.StringRes
import com.raapp.wishlist.BaseFragment
import com.raapp.wishlist.BuildConfig
import com.raapp.wishlist.Constants.EMPTY_STRING

import com.raapp.wishlist.R
import com.raapp.wishlist.models.PrivacyType
import com.raapp.wishlist.models.Wish
import com.raapp.wishlist.repository.WishRepository
import com.raapp.wishlist.repository.WishRepositoryImpl
import com.raapp.wishlist.repository.WishRepositoryMockImpl
import com.raapp.wishlist.utils.NonBlankRule
import com.raapp.wishlist.utils.SimpleTextWatcher
import com.wajahatkarim3.easyvalidation.core.Validator
import kotlinx.android.synthetic.main.fragment_wish_edit.*
import kotlinx.android.synthetic.main.fragment_wish_edit.view.*

class WishEditFragment : BaseFragment() {
    var wish: Wish? = null
        private set(value) {
            field = value
        }

    private var wishRepository: WishRepository? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            wish = it.getSerializable(WishEditFragment::wish.name) as? Wish
        }
    }

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
        val view = inflater.inflate(R.layout.fragment_wish_edit, container, false)
        view.link_edit_input.addTextChangedListener(
            SimpleTextWatcher {
                linkErrorMessage(null)
            }
        )
        view.wish_edit_title_input.addTextChangedListener(
            SimpleTextWatcher {
                titleErrorMessage(null)
            }
        )
        return view
    }

    override fun onStart() {
        super.onStart()
        initToolbar()
    }

    private fun validateFields() {
        var hasErrors = false
        // get values
        val title = wish_edit_title_input.text?.toString() ?: EMPTY_STRING
        val link = link_edit_input.text?.toString()
        val description = description_edit_input.text?.toString()
        val privacyType = PrivacyType.getById(radio_group_privacy.checkedRadioButtonId)
        // validate values
        Validator(title)
            .addRule(NonBlankRule())
            .addErrorCallback {
                hasErrors = true
                titleErrorMessage(R.string.edit_screen_title_error_empty)
            }
            .check()
        if (link != null) {
            Validator(link)
                .validUrl()
                .addErrorCallback {
                    hasErrors = true
                    linkErrorMessage(R.string.edit_screen_link_error_format)
                }
                .check()
        }

        if (hasErrors) {
            Toast.makeText(context, "Errors in inputs", LENGTH_LONG).show()
        } else {
            val wish = Wish(
                title = title,
                link = link,
                description = description,
                privacy = privacyType.ordinal
            )
            saveNewWish(wish)
        }
    }

    private fun titleErrorMessage(@StringRes message: Int? = null) {
        if (message == null) {
            wish_edit_title_layout.isErrorEnabled = false
        } else {
            wish_edit_title_layout.error = getString(message)
        }
    }

    private fun linkErrorMessage(@StringRes message: Int? = null) {
        if (message == null) {
            link_edit_layout.isErrorEnabled = false
        } else {
            link_edit_layout.error = getString(message)
        }
    }

    private fun saveNewWish(wish: Wish) {
        wishRepository?.addNewWishLocal(wish)
        Toast.makeText(context, "Wish successful added", LENGTH_LONG).show()
        activity?.onBackPressed()
    }


    private fun initToolbar() {
        getToolbar()?.run {
            inflateMenu(R.menu.wish_edit_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.edit_save_menu_item -> {
                        validateFields()
                        true
                    }
                    else -> false
                }
            }
            setNavigationIcon(R.drawable.ic_arrow_back_light)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    companion object {
        // TODO
        @JvmStatic
        fun newInstance(wishForEdit: Wish? = null) =
            WishEditFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(WishEditFragment::wish.name, wishForEdit)
                }
            }
    }
}
