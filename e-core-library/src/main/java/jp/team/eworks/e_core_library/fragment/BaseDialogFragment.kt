package jp.team.eworks.e_core_library.fragment

import android.content.Context
import android.content.DialogInterface
import androidx.fragment.app.DialogFragment
import jp.team.eworks.e_core_library.fragment.fragment_interface.DialogParentInterface

abstract class BaseDialogFragment : DialogFragment() {
    abstract val dialogId: Int
    abstract val dialogTag: String

    private var parentInterface: DialogParentInterface? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val t = targetFragment
        val p = parentFragment
        when {
            t is DialogParentInterface -> {
                parentInterface = t
            }
            p is DialogParentInterface -> {
                parentInterface = p
            }
            context is DialogParentInterface -> {
                parentInterface = context
            }
        }
    }

    override fun onDetach() {
        parentInterface = null
        super.onDetach()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        parentInterface?.onDialogDismiss(dialogId)
    }
}
