package com.fred.earthquaketracker.dagger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Reusable
import javax.inject.Inject
import javax.inject.Provider

/**
 * Provide ViewModelFactory through dagger
 */

@Reusable
class ProvidingViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var provider: Provider<out ViewModel>? = creators[modelClass]
        if (provider == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    provider = value
                    break
                }
            }
        }
        provider?.let {
            try {
                @Suppress("UNCHECKED_CAST")
                return provider.get() as T
            } catch (e: ClassCastException) {
                throw IllegalStateException("Unable to case output of $provider to $modelClass", e)
            }
        } ?: throw  IllegalArgumentException("Unable to provide $modelClass")
    }

}