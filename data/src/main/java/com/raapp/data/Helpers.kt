package com.raapp.data

import com.raapp.wishlist.data.BuildConfig

fun Any.logLine(message: String) {
    if (BuildConfig.DEBUG) {
        val className = javaClass.name
        val methodName = javaClass.enclosingMethod?.name
        println(">>>>> $className $methodName $message")
    }
}