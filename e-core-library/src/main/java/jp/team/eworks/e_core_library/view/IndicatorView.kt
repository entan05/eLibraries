package jp.team.eworks.e_core_library.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import jp.team.eworks.e_core_library.R
import jp.team.eworks.e_core_library.view.view_interface.IndicatorViewInterface

class IndicatorView: ConstraintLayout, IndicatorViewInterface {

    private val messageTextView: TextView

    var message: String?
        set(value) {
            messageTextView.text = value
        }
        get() = messageTextView.text.toString()

    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttrs: Int): super(
        context,
        attrs,
        defStyleAttrs
    ) {
        View.inflate(context, R.layout.view_indicator, this)

        messageTextView = findViewById(R.id.indicator_message)

        isClickable = true
        visibility = View.GONE
    }

    override val isVisibleIndicator: Boolean
        get() = (visibility == View.VISIBLE)

    override fun showIndicator() {
        visibility = View.VISIBLE
    }

    override fun hideIndicator() {
        visibility = View.GONE
        message = null
    }
}
