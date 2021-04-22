package jp.team.eworks.e_core_library.extention

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import jp.team.eworks.e_core_library.utils.Extra
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * FragmentのViewBindingを行う
 */
inline fun <reified T: ViewBinding> Fragment.fragmentViewBinding(): ReadOnlyProperty<Fragment, T> =
    FragmentViewProvider(T::class.java)

class FragmentViewProvider<T: ViewBinding>(private val clazz: Class<T>):
    ReadOnlyProperty<Fragment, T> {
    var field: Any? = Extra.UninitializedValueForExtra

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        if (field == Extra.UninitializedValueForExtra) {
            field =
                clazz.getMethod("bind", View::class.java).invoke(null, thisRef.requireView())
        }
        @Suppress("UNCHECKED_CAST")
        return field as T
    }
}
