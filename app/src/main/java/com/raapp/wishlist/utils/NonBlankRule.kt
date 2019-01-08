package com.raapp.wishlist.utils

import com.wajahatkarim3.easyvalidation.core.rules.BaseRule

class NonBlankRule : BaseRule {
    override fun validate(text: String): Boolean = !text.isBlank()

    override fun getErrorMessage(): String = "Can't be empty!"
}