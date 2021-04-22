package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.team.eworks.e_core_library.fragment.BaseDialogFragment
import jp.team.eworks.e_core_library.utils.Extra
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSampleDialogBinding
import jp.team.eworks.e_core_library.extention.fragmentViewBinding

class SampleDialogFragment: BaseDialogFragment() {
    companion object {
        fun newInstance(message: String): SampleDialogFragment {
            return SampleDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(SampleDialogFragment::dialogMessage.name, message)
                }
            }
        }
    }

    override val dialogId: Int
        get() = 10

    override val dialogTag: String
        get() = "SampleDialogFragment"

    private val dialogMessage: String by Extra.Fragment()

    private val binding: FragmentSampleDialogBinding by fragmentViewBinding()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_sample_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dialogMessage.text = dialogMessage

        binding.dialogButton.setOnClickListener {
            dismiss()
        }
    }
}
