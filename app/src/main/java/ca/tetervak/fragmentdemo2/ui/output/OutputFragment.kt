package ca.tetervak.fragmentdemo2.ui.output

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ca.tetervak.fragmentdemo2.R
import ca.tetervak.fragmentdemo2.databinding.FragmentOutputBinding
import ca.tetervak.fragmentdemo2.ui.dialogs.DateTimeDialog
import java.util.*

class OutputFragment : Fragment() {

    companion object {
        const val CHECK_IN_DATE_REQUEST_KEY = "checkInDateRequestKey"
        const val CHECK_OUT_DATE_REQUEST_KEY = "checkOutDateRequestKey"
    }

    private var _binding: FragmentOutputBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OutputViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOutputBinding.inflate(inflater, container, false)

        // data-bind the viewModel
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.checkInOutput.setOnClickListener{ openCheckInDialog() }
        binding.checkOutOutput.setOnClickListener{ openCheckOutDialog() }

        parentFragmentManager.setFragmentResultListener(
            CHECK_IN_DATE_REQUEST_KEY, viewLifecycleOwner){ _, bundle ->
            @Suppress("DEPRECATION")
            val date = bundle.getSerializable(DateTimeDialog.DATE) as Date
            viewModel.setCheckInDate(date)
        }

        parentFragmentManager.setFragmentResultListener(
            CHECK_OUT_DATE_REQUEST_KEY, viewLifecycleOwner){ _, bundle ->
            @Suppress("DEPRECATION")
            val date = bundle.getSerializable(DateTimeDialog.DATE) as Date
            viewModel.setCheckOutDate(date)
        }

        return binding.root
    }

    private fun openCheckInDialog() {
        val fragment = DateTimeDialog.newInstance(
            requestKey = CHECK_IN_DATE_REQUEST_KEY,
            message = getString(R.string.check_in_dialog_message),
            date = viewModel.checkInDate
        )
        fragment.show(parentFragmentManager, null)
    }

    private fun openCheckOutDialog() {
        val fragment = DateTimeDialog.newInstance(
            requestKey = CHECK_OUT_DATE_REQUEST_KEY,
            message = getString(R.string.check_out_dialog_message),
            date = viewModel.checkOutDate
        )
        fragment.show(parentFragmentManager, null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}