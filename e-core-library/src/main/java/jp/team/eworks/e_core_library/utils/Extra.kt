package jp.team.eworks.e_core_library.utils

import androidx.lifecycle.ViewModelProvider
import jp.team.eworks.e_core_library.viewmodel.BaseViewModel
import android.app.Activity as StandardActivity
import androidx.fragment.app.Fragment as StandardFragment
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// from: https://speakerdeck.com/sakuna63/kotlins-delegated-properties-x-android
@Suppress("UNCHECKED_CAST")
class Extra {
    companion object {
        inline fun <reified T : BaseViewModel> createViewModel(): Lazy<T> =
            lazy { ViewModelProvider.NewInstanceFactory().create(T::class.java) }
    }

    private object UninitializedValueForExtra

    class Activity<T> : ReadWriteProperty<StandardActivity, T> {
        var field: Any? = UninitializedValueForExtra

        override fun getValue(thisRef: StandardActivity, property: KProperty<*>): T {
            if (field == UninitializedValueForExtra) {
                field = thisRef.intent.extras?.get(property.name)
            }
            return field as T
        }

        override fun setValue(thisRef: StandardActivity, property: KProperty<*>, value: T) {
            field = value
        }
    }

    class Fragment<T> : ReadWriteProperty<StandardFragment, T> {
        var field: Any? = UninitializedValueForExtra

        override fun getValue(thisRef: StandardFragment, property: KProperty<*>): T {
            if (field == UninitializedValueForExtra) {
                field = thisRef.arguments?.get(property.name)
            }
            return field as T
        }

        override fun setValue(thisRef: StandardFragment, property: KProperty<*>, value: T) {
            field = value
        }
    }
}
