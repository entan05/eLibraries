package jp.team.eworks.e_core_library.view.view_interface

interface SnackBarViewInterface {
    /**
     * 非表示になるまでの時間
     */
    val hideDelayMillis: Long

    /**
     *  interface内でbacking fieldを持てないので実装側でbacking fieldを用意すること
     */
    var hiddenProcess: (() -> Unit)?

    /**
     * 即時非表示要求
     */
    fun requestHidden() {
        hiddenProcess?.invoke()
    }
}
