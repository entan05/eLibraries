package com.example.myapplication.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.team.eworks.e_core_library.fragment.BaseFragment
import jp.team.eworks.e_core_library.view.SnackBarView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSampleBinding
import com.example.myapplication.databinding.ItemSampleIndicatorBinding
import jp.team.eworks.e_core_library.extention.fragmentViewBinding

class SampleFragment : BaseFragment() {
    companion object {
        fun createInstance(): SampleFragment =
                SampleFragment()
    }

    private enum class ListItem(val labelText: String) {
        ShowIndicator("Show Indicator"),
        ShowSnackBar("Show SnackBar"),
        ShowDialog("Show Dialog"),
        ShowQueueDialog("Show Dialog(Queue)"),
    }

    private lateinit var mainHandler: Handler

    private val binding: FragmentSampleBinding by fragmentViewBinding()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sample, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainHandler = Handler(Looper.getMainLooper())

        initView()
    }

    override fun onStart() {
        super.onStart()
        val temp = _onStart
    }

    private val _onStart: Unit by lazy {
        binding.recyclerView.apply {
            adapter = ListAdapter()
            layoutManager = LinearLayoutManager(requireContext())
        }
        return@lazy
    }

    private fun initView() {

        binding.root.setOnClickListener {
            // do nothing
        }
    }

    private fun doProcess(item: ListItem) {
        when (item) {
            ListItem.ShowIndicator -> {
                showIndicator()
                mainHandler.postDelayed({
                    hideIndicator()
                }, 3000)
            }
            ListItem.ShowSnackBar -> {
                showSnackBar(SnackBarView.newInstance(requireContext(), "Fragment Snack"))
            }
            ListItem.ShowDialog -> {
                showDialog(SampleDialogFragment.newInstance("Sample"))
            }
            ListItem.ShowQueueDialog -> {
                showDialog(SampleDialogFragment.newInstance("Queue: 1"))
                mainHandler.postDelayed({
                    showDialog(SampleDialogFragment.newInstance("Queue: 2"))
                    showDialog(SampleDialogFragment.newInstance("Queue: 3"))
                }, 300)
            }
        }
    }

    private inner class ListAdapter: RecyclerView.Adapter<ListViewHolder>() {
        override fun getItemCount(): Int = ListItem.values().size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
            return ListViewHolder(
                ItemSampleIndicatorBinding.inflate(
                    LayoutInflater.from(
                        requireContext()
                    ), parent, false
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
