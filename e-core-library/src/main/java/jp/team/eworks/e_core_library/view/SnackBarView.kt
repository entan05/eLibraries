package jp.team.eworks.e_core_library.view

import android.content.Context
import android.graphics.Outline
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import jp.team.eworks.e_core_library.R
import jp.team.eworks.e_core_library.view.view_interface.SnackBarViewInterface

class SnackBarView: ConstraintLayout, SnackBarViewInterface {

    companion object {
        fun newInstance(context: Context, message: String): SnackBarView =
            SnackBarView(context).also {
                it.message = message
            }
    }

    override val hideDelayMillis: Long
        get() = 5000

    private var _hiddenProcess: (() -> Unit)? = null
    override var hiddenProcess: (() -> Unit)?
        get() = _hiddenProcess
        set(value) {
            _hiddenProcess = value
        }

    var message: String
        set(value) {
            snackBarTextView.text = value
        }
        get() = snackBarTextView.text.toString()

    private val snackBarTextView: TextView

    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttrs: Int): super(
        context,
        attrs,
        defStyleAttrs
    ) {
        View.inflate(context, R.layout.view_snack_bar, this)

        snackBarTextView = findViewById(R.id.snack_bar_text)

        findViewById<View>(R.id.snack_bar_background).apply {
            outlineProvider =
                object: ViewOutlineProvider() {
                    override fun getOutline(view: View?, outline: Outline?) {
                        view?.let {
                            val radius = TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                6f,
                                context.resources.displayMetrics
                            )
                            outline?.setRoundRect(0, 0, it.width, it.height, radius)
                            it.clipToOutline = true
                        }
                    }
                }
            setOnClickListener { requestHidden() }
        }
    }
}
