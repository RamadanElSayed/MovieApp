package com.app.movieapp.common.presentation.resource

import android.content.Context
import androidx.annotation.StringRes

/**
 * Lets ViewModels resolve localized strings without holding a Context reference directly, keeping
 * them unit-testable (provide a fake in tests). Backed by the application context at runtime.
 */
interface ResourceProvider {
    fun getString(@StringRes resId: Int): String
    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String
}

class AndroidResourceProvider(private val context: Context) : ResourceProvider {
    override fun getString(resId: Int): String = context.getString(resId)
    override fun getString(resId: Int, vararg formatArgs: Any): String =
        context.getString(resId, *formatArgs)
}
