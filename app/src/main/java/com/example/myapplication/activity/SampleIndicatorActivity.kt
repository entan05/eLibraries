package com.example.myapplication.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.team.eworks.e_core_library.activity.IndicatorActivity
import jp.team.eworks.e_core_library.utils.Extra
import jp.team.eworks.e_core_library.view.IndicatorView
import jp.team.eworks.e_core_library.view.SnackBarView
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySampleIndicatorBinding
import com.example.myapplication.databinding.ItemSampleIndicatorBinding
import com.example.myapplication.fragment.SampleDialogFragment
import com.example.myapplication.fragment.SampleFragment

class SampleIndicatorActivity : IndicatorActivity<IndicatorView>() {
    companion object {
        fun start(context: Context, finishEnterAnim: Int, finishExitAnim: Int) {
            context.startActivity(Intent(context, SampleIndicatorActivity::class.java).apply {
                putExtra(SampleIndicatorActivity::finishEnterAnim.name, finishEnterAnim)
                putExtra(SampleIndicatorActivity::finishExitAnim.name, finishExitAnim)
            })
        }
    }

    private enum class ListItem(val labelText: String) {
        ShowIndicator("Show Indicator"),
        ShowSnackBar("Show SnackBar"),
        ShowFragment("Show Fragment"),
        ShowDialog("Show Dialog"),
        ShowQueueDialog("Show Dialog(Queue)"),
    }

    private val binding by activityBinding<ActivitySampleIndicatorBinding>()

    private lateinit var mainHandler: Handler

    private val finishEnterAnim: Int by Extra.Activity()
    private val finishExitAnim: Int by Extra.Activity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_indicator)

        mainHandler = Handler(Looper.getMainLooper())

        initView()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(finishEnterAnim, finishExitAnim)
    }

    override fun createIndicatorView(): IndicatorView = IndicatorView(this)

    private fun initView() {
        binding.recyclerView.apply {
            adapter = ListAdapter()
            layoutManager = LinearLayoutManager(this@SampleIndicatorActivity)
        }
    }

    private fun doProcess(item: ListItem) {
        when (item) {
            ListItem.ShowIndicator -> {
                indicatorView.message = "ホゲ"
                showIndicator()
                mainHandler.postDelayed({
                    hideIndicator()
                }, 5000)
            }
            ListItem.ShowSnackBar -> {
                showSnackBar(
                    SnackBarView.newInstance(
                        this@SampleIndicatorActivity,
                        "Sample SnackBar"
                    )
                )
            }
            ListItem.ShowFragment -> {
                showFragment(
                    SampleFragment.createInstance(),
                    ActivityTransitionSlideInTop, ActivityTransitionSlideOutBottom
                )
            }
            ListItem.ShowDialog -> {
                val dialog = SampleDialogFragment.newInstance("single")
                showDialog(dialog)
            }
            ListItem.ShowQueueDialog -> {
                showDialog(SampleDialogFragment.newInstance("queue: 1"))
                mainHandler.postDelayed({
                    showDialog(SampleDialogFragment.newInstance("queue: 2"))
                }, 300)
                mainHandler.postDelayed({
                    showDialog(SampleDialogFragment.newInstance("queue: 3"))
                }, 600)
            }
        }
    }

    private inner class ListAdapter : RecyclerView.Adapter<ListViewHolder>() {
        override fun getItemCount(): Int = ListItem.values().size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
            return ListViewHolder(
                ItemSampleIndicatorBinding.inflate(
                    LayoutInflater.from(this@SampleIndicatorActivity),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
            holder.onBind(ListItem.values().getOrNull(position))
        }
    }

    private inner class ListViewHolder(private val binding: ItemSampleIndicatorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: ListItem?) {
            binding.apply {
                if (item == null) {
                    label.text = ""
                } else {
                    label.text = item.labelText
                    root.setOnClickListener {
                        doProcess(item)
                    }
                }
            }
        }
    }
}
