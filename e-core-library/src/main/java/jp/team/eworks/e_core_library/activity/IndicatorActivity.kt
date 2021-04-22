package jp.team.eworks.e_core_library.activity

import android.view.View
import jp.team.eworks.e_core_library.databinding.ActivityBaseBinding
import jp.team.eworks.e_core_library.view.view_interface.IndicatorViewInterface

abstract class IndicatorActivity<T>: BaseActivity() where T: View, T: IndicatorViewInterface {

    private lateinit var binding: ActivityBaseBinding

    protected lateinit var indicatorView: T

    val isShowIndicator: Boolean
        get() = indicatorView.isVisibleIndicator

    override fun setContentView(view: View?) {
        super.setContentView(view)
        view?.let {
            binding = ActivityBaseBinding.bind(it)
        }

        initIndicatorView()
    }

    override fun handleBackPressed(): Boolean {
        if (super.handleBackPressed()) return true

        return indicatorView.isVisibleIndicator
    }

    /**
     * 全画面IndicatorViewを生成する
     * @return 全画面IndicatorView
     */
    abstract fun createIndicatorView(): T

    /**
     * 全画面IndicatorViewを表示する
     */
    fun showIndicator() {
        indicatorView.showIndicator()
    }

    /**
     * 全画面IndicatorViewを非表示にする
     */
    fun hideIndicator() {
        indicatorView.hideIndicator()
    }

    private fun initIndicatorView() {
        indicatorView = createIndicatorView()

        binding.indicatorContainer.addView(indicatorView)
    }
}
