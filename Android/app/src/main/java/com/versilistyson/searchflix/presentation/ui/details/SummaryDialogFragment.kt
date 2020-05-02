package com.versilistyson.searchflix.presentation.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs

import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.databinding.FragmentSummaryDialogBinding
import com.versilistyson.searchflix.presentation.ui.common.activity.DataBindingScreen

class SummaryDialogFragment : DialogFragment(), DataBindingScreen<FragmentSummaryDialogBinding> {

    override lateinit var binding: FragmentSummaryDialogBinding

    val args: SummaryDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_summary_dialog,container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvFullSummary.text = args.summary
    }
}
