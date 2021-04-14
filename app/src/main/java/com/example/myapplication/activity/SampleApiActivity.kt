package com.example.myapplication.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import jp.team.eworks.e_core_library.activity.IndicatorActivity
import jp.team.eworks.e_core_library.utils.Extra
import jp.team.eworks.e_core_library.view.IndicatorView
import jp.team.eworks.e_core_library.view.SnackBarView
import jp.team.eworks.e_core_library.viewmodel.BaseViewModel
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySampleApiBinding
import com.example.myapplication.viewmodel.SampleViewModel

class SampleApiActivity : IndicatorActivity<IndicatorView>() {
    companion object {
        fun start(context: Context, finishEnterAnim: Int, finishExitAnim: Int) {
            context.startActivity(Intent(context, SampleApiActivity::class.java).apply {
                putExtra(SampleApiActivity::finishEnterAnim.name, finishEnterAnim)
                putExtra(SampleApiActivity::finishExitAnim.name, finishExitAnim)
            })
        }
    }

    private val binding by activityBinding<ActivitySampleApiBinding>()

    private val finishEnterAnim: Int by Extra.Activity()
    private val finishExitAnim: Int by Extra.Activity()

    private val sampleViewModel: SampleViewModel by Extra.createViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_api)

        sampleViewModel.apply {
            observeSnackApiResponse(this@SampleApiActivity) { str ->
                binding.response.text = str
            }

            observeState(this@SampleApiActivity) { state ->
                when (state) {
                    is BaseViewModel.State.Loading -> showIndicator()
                    is BaseViewModel.State.Loaded -> hideIndicator()
                    else -> {
                        hideIndicator()
                        showSnackBar(SnackBarView.newInstance(this@SampleApiActivity, "エラー"))
                    }
                }
            }
        }

        binding.executeApi.apply {
            setOnClickListener {
                sampleViewModel.getSnacks()
            }
        }
    }

    override fun createIndicatorView(): IndicatorView {
        return IndicatorView(this)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(finishEnterAnim, finishExitAnim)
    }
}
