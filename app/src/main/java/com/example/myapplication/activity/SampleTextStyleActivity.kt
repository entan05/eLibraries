package com.example.myapplication.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.myapplication.R
import jp.team.eworks.e_core_library.activity.BaseActivity
import jp.team.eworks.e_core_library.utils.Extra

class SampleTextStyleActivity : BaseActivity() {
    companion object {
        fun start(context: Context, finishEnterAnim: Int, finishExitAnim: Int) {
            context.startActivity(Intent(context, SampleTextStyleActivity::class.java).apply {
                putExtra(SampleTextStyleActivity::finishEnterAnim.name, finishEnterAnim)
                putExtra(SampleTextStyleActivity::finishExitAnim.name, finishExitAnim)
            })
        }
    }

    private val finishEnterAnim: Int by Extra.Activity()
    private val finishExitAnim: Int by Extra.Activity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_text_style)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(finishEnterAnim, finishExitAnim)
    }
}
