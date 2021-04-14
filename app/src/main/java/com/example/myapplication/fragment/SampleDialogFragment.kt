package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import jp.team.eworks.e_core_library.fragment.BaseDialogFragment
import jp.team.eworks.e_core_library.utils.Extra
import com.example.myapplication.R

class SampleDialogFragment : BaseDialogFragment() {
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

    private lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_sample_dialog, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rootView.findViewById<TextView>(R.id.dialog_message).apply {
            text = dialogMessage
        }

        rootView.findViewById<Button>(R.id.dialog_button).apply {
            setOnClickListener {
                dismiss()
            }
        }
    }
}
