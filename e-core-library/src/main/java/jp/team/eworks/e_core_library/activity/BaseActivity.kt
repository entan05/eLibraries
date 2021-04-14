package jp.team.eworks.e_core_library.activity

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.activity.OnBackPressedCallback
import androidx.annotation.AnimRes
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.viewbinding.ViewBinding
import jp.team.eworks.e_core_library.R
import jp.team.eworks.e_core_library.databinding.ActivityBaseBinding
import jp.team.eworks.e_core_library.fragment.BaseDialogFragment
import jp.team.eworks.e_core_library.fragment.BaseFragment
import jp.team.eworks.e_core_library.fragment.fragment_interface.DialogParentInterface
import jp.team.eworks.e_core_library.view.view_interface.SnackBarViewInterface
import java.util.*

abstract class BaseActivity : AppCompatActivity(), DialogParentInterface {

    @Suppress("unused")
    companion object {
        /** 右から左方向へ進入 */
        val ActivityTransitionSlideInLeft = R.anim.activity_slide_in_left
        /** 左方向へ退場 */
        val ActivityTransitionSlideOutLeft = R.anim.activity_slide_out_left
        /** 下から上方向へ進入 */
        val ActivityTransitionSlideInTop = R.anim.activity_slide_in_top
        /** 上方向へ退場 */
        val ActivityTransitionSlideOutTop = R.anim.activity_slide_out_top
        /** 左から右方向へ進入 */
        val ActivityTransitionSlideInRight = R.anim.activity_slide_in_right
        /** 右方向へ退場 */
        val ActivityTransitionSlideOutRight = R.anim.activity_slide_out_right
        /** 上から下方向へ進入 */
        val ActivityTransitionSlideInBottom = R.anim.activity_slide_in_bottom
        /** 下方向へ退場 */
        val ActivityTransitionSlideOutBottom = R.anim.activity_slide_out_bottom
        /** なし */
        const val ActivityTransitionNone = 0
    }

    protected val isShowDialog: Boolean
        get() {
            supportFragmentManager.fragments.forEach {
                if (it is BaseDialogFragment) return true
            }
            return false
        }

    private lateinit var binding: ActivityBaseBinding

    private lateinit var onBackPressedCallback: OnBackPressedCallback

    private val dialogQueue: Queue<BaseDialogFragment> = LinkedList<BaseDialogFragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!handleBackPressed()) {
                    finish()
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun setContentView(layoutResID: Int) {
        val inflater = LayoutInflater.from(this)
        binding = ActivityBaseBinding.inflate(inflater)
        binding.activityContainer.addView(inflater.inflate(layoutResID, null))
        setContentView(binding.root)
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)

        binding.screenCover.apply {
            setOnClickListener {
                // do nothing
            }
            visibility = View.GONE
        }
    }

    @CallSuper
    override fun onDialogDismiss(dialogId: Int) {
        val queue = dialogQueue.poll()
        queue?.also { dialog ->
            dialog.show(supportFragmentManager, dialog.dialogTag)
        } ?: run {
            hideScreenCover()
        }
    }

    final override fun onBackPressed() {
        super.onBackPressed()
    }

    /**
     * バックキーが処理済みかを返す
     * @return true: 処理済み
     */
    open fun handleBackPressed(): Boolean {
        if (binding.screenCover.visibility == View.VISIBLE) {
            return true
        }

        if (supportFragmentManager.backStackEntryCount > 0) {
            when (val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)) {
                is BaseFragment -> {
                    if (!fragment.handleBackPressed()) {
                        supportFragmentManager.popBackStack()
                    }
                }
                else -> supportFragmentManager.popBackStack()
            }
            return true
        }
        return false
    }

    fun showFragment(
            fragment: BaseFragment,
            @AnimRes enter: Int? = null,
            @AnimRes exit: Int? = null
    ) {
        supportFragmentManager.beginTransaction()
                .apply {
                    if (enter != null && exit != null) {
                        setCustomAnimations(enter, exit, enter, exit)
                    }
                }.add(R.id.fragment_container, fragment)
                .addToBackStack(fragment.javaClass.simpleName)
                .commit()
    }

    fun showDialog(dialog: BaseDialogFragment) {
        if (isShowDialog) {
            dialogQueue.add(dialog)
            return
        }
        dialog.show(supportFragmentManager, dialog.dialogTag)
        showScreenCover()
    }

    /**
     * スナックバーを表示する
     * @param snackBar 表示するスナックバー
     */
    fun showSnackBar(snackBar: SnackBarViewInterface) {
        (snackBar as? View)?.let { view ->
            view.apply {
                layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
                visibility = View.VISIBLE
                alpha = 0f
            }
            binding.snackBarContainer.addView(view)

            ObjectAnimator.ofFloat(view, "alpha", 0f, 1f).apply {
                duration = snackBar.hideDelayMillis / 2
                interpolator = DecelerateInterpolator(3f)
                repeatMode = ValueAnimator.REVERSE
                repeatCount = 1
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationEnd(animation: Animator?) {
                        binding.snackBarContainer.removeView(view)
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                        binding.snackBarContainer.removeView(view)
                    }

                    override fun onAnimationStart(animation: Animator?) {}
                    override fun onAnimationRepeat(animation: Animator?) {}
                })
            }.start()
        }
    }

    /**
     * 透明な操作不能Viewを表示する
     */
    fun showScreenCover() {
        binding.screenCover.visibility = View.VISIBLE
    }

    /**
     * 透明な操作不能Viewを非表示にする
     */
    fun hideScreenCover() {
        binding.screenCover.visibility = View.GONE
    }

    /**
     * ActivityのviewBindingを行う
     * アクセスは {@code setContentView(layoutResID: Int)} の呼び出し後に行うこと
     * from: https://matsudamper.hatenablog.com/entry/2019/10/29/013329
     */
    inline fun <reified T : ViewBinding> activityBinding(): Lazy<T> =
        lazy {
            activityBindingProcess(
                T::class.java,
                findViewById<ViewGroup>(R.id.activity_container)[0]
            )
        }

    @Suppress("UNCHECKED_CAST")
    fun <T : ViewBinding> activityBindingProcess(clazz: Class<T>, view: View): T {
        return clazz.getMethod("bind", View::class.java).invoke(null, view) as T
    }
}
