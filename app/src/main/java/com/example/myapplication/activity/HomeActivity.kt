package com.example.myapplication.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.team.eworks.e_core_library.activity.BaseActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityHomeBinding
import com.example.myapplication.databinding.ItemHomeBinding
import jp.team.eworks.e_core_library.extention.afterMeasured
import timber.log.Timber

class HomeActivity: BaseActivity() {

    private enum class ListItem(val itemLabel: String, val isEnable: Boolean = true) {
        IndicatorSample("Sample Indicator Activity"),
        ApiSample("Sample Api Activity"),
        TextStyleSample("Sample Text Style"),
    }

    private val binding by activityBinding<ActivityHomeBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initView()
    }

    private fun initView() {
        binding.recyclerView.apply {
            adapter = HomeAdapter()
            layoutManager =
                LinearLayoutManager(this@HomeActivity, LinearLayoutManager.VERTICAL, false)

            afterMeasured {
                Timber.d("recyclerView afterMeasured called.")
            }
        }
    }

    private fun switchActivity(item: ListItem?) {
        when (item) {
            ListItem.IndicatorSample -> {
                SampleIndicatorActivity.start(
                    this@HomeActivity,
                    ActivityTransitionSlideInRight, ActivityTransitionSlideOutRight
                )
            }
            ListItem.ApiSample -> {
                SampleApiActivity.start(
                    this@HomeActivity,
                    ActivityTransitionSlideInRight, ActivityTransitionSlideOutRight
                )
            }
            ListItem.TextStyleSample -> {
                SampleTextStyleActivity.start(
                    this@HomeActivity,
                    ActivityTransitionSlideInRight, ActivityTransitionSlideOutRight
                )
            }
            else -> return
        }
        overridePendingTransition(ActivityTransitionSlideInLeft, ActivityTransitionSlideOutLeft)
    }

    private inner class HomeAdapter: RecyclerView.Adapter<HomeViewHolder>() {
        override fun getItemCount(): Int = ListItem.values().size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
            return HomeViewHolder(
                ItemHomeBinding.inflate(
                    LayoutInflater.from(this@HomeActivity),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
            holder.onBind(ListItem.values().getOrNull(position))
        }
    }

    private inner class HomeViewHolder(private val binding: ItemHomeBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: ListItem?) {
            binding.apply {
                label.text = item?.itemLabel ?: ""

                if (item?.isEnable == true) {
                    disableBackground.visibility = View.GONE
                    root.setOnClickListener {
                        switchActivity(item)
                    }
                } else {
                    disableBackground.visibility = View.VISIBLE
                }
            }
        }
    }
}
