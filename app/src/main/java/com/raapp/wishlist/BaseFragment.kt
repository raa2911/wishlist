package com.raapp.wishlist

import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 *
 */
abstract class BaseFragment : Fragment() {


    protected fun getToolbar(): Toolbar? {
        return this.view?.findViewById(R.id.toolbar)
    }

    protected fun logMessage(message: String?, exception: Exception? = null) {
        if (!BuildConfig.DEBUG) return
        if (message != null) {
            Log.e(Constants.LOG_TAG, message, exception)
        }
    }
}
