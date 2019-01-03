package com.raapp.wishlist

import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import java.lang.Exception

/**
 * A base [Fragment] subclass.
 * Recommend to use as parent for all Fragment screens in project.
 */
abstract class BaseFragment : Fragment() {

    /**
     * Return [Toolbar] by static id if it exist on current [view][getView]
     */
    protected fun getToolbar(): Toolbar? {
        return this.view?.findViewById(R.id.toolbar)
    }

    /**
     * Send a log message if [BuildConfig] is in [DEBUG][BuildConfig.DEBUG] mode.
     * @param exception - Log the exception stack trace if it present.
     */
    protected fun logMessage(message: String?, exception: Exception? = null) {
        if (!BuildConfig.DEBUG) return
        if (exception != null) {
            Log.e(Constants.LOG_TAG, message, exception)
        } else {
            Log.d(Constants.LOG_TAG, message)
        }
    }

    protected fun onBackPressed() {
        activity?.onBackPressed()
    }
}
