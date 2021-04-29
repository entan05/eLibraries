package jp.team.eworks.e_core_library.fragment

import android.content.Context
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import jp.team.eworks.e_core_library.activity.BaseActivity
import jp.team.eworks.e_core_library.activity.IndicatorActivity
import jp.team.eworks.e_core_library.fragment.fragment_interface.DialogParentInterface
import jp.team.eworks.e_core_library.view.view_interface.SnackBarViewInterface
import java.util.*

abstract class BaseFragment: Fragment, DialogParentInterface {
    constructor(): super()
    constructor(@LayoutRes layoutId: Int): super(layoutId)

    protected var baseActivity: BaseActivity? = null
    protected val requireBaseActivity: BaseActivity
        get() = baseActivity!!

    protected val isShowDialog: Boolean
        get() {
            childFragmentManager.fragments.forEach {
                if (it is BaseDialogFragment) return true
            }
            return false
        }

    protected val dialogQueue: Queue<BaseDialogFragment> = LinkedList<BaseDialogFragment>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            baseActivity = context
        }
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    @CallSuper
    override fun onDialogDismiss(dialogId: Int) {
        val queue = dialogQueue.poll()
        queue?.also { dialog ->
            dialog.show(childFragmentManager, dialog.dialogTag)
        } ?: run {
            hideScreenCover()
        }
    }

    open fun handleBackPressed(): Boolean {
        if ((baseActivity as? IndicatorActivity<*>)?.isShowIndicator == true) {
            return true
        }
        return false
    }

    open fun showIndicator() {
        (baseActivity as? IndicatorActivity<*>)?.showIndicator()
    }

    open fun hideIndicator() {
        (baseActivity as? IndicatorActivity<*>)?.hideIndicator()
    }

    fun showDialog(dialog: BaseDialogFragment) {
        if (isShowDialog) {
            dialogQueue.add(dialog)
            return
        }
        dialog.show(childFragmentManager, dialog.dialogTag)
        showScreenCover()
    }

    open fun showSnackBar(snackBar: SnackBarViewInterface) {
        baseActivity?.showSnackBar(snackBar)
    }

    fun showScreenCover() {
        baseActivity?.showScreenCover()
    }

    fun hideScreenCover() {
        baseActivity?.hideScreenCover()
    }
}
