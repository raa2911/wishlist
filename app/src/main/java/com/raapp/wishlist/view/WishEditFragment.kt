package com.raapp.wishlist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.raapp.wishlist.BaseFragment

import com.raapp.wishlist.R
import com.raapp.wishlist.models.PrivacyType
import com.raapp.wishlist.models.Wish
import com.raapp.wishlist.repository.WishRepository
import com.raapp.wishlist.repository.WishRepositoryImpl
import com.raapp.wishlist.utils.NonBlankRule
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.android.synthetic.main.fragment_wish_edit.*
import kotlin.concurrent.thread

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [WishEditFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [WishEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class WishEditFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var wishRepository: WishRepository? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.context?.also {
            wishRepository = WishRepositoryImpl.getInstance(it)
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wish_edit, container, false)
    }

    override fun onStart() {
        super.onStart()
        initToolbar()
    }

    private fun validateFields() {
        wish_edit_input
            .validator()
            .addRule(NonBlankRule())
            .check()

        val title = wish_edit_input.text?.toString()
        val link = link_edit_input.text?.toString()
        val description = description_edit_input.text?.toString()
        val privacyType = PrivacyType.getById(radio_group_privacy.checkedRadioButtonId)
        if (title.isNullOrEmpty()) {
            Toast.makeText(context, "Please fill title", LENGTH_LONG).show()
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
                // TODO
                onBackPressed()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WishEditFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WishEditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
