package ca.tetervak.fragmentdemo2.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import ca.tetervak.fragmentdemo2.databinding.DialogDateTimeBinding
import com.google.android.material.tabs.TabLayout
import java.util.*

class DateTimeDialog : DialogFragment() {

    companion object {
        private const val REQUEST_KEY = "requestKey"
        private const val MESSAGE = "message"
        const val DATE = "date"

        fun newInstance(
            requestKey: String,
            message: String,
            date: Date = Date()
        ): DateTimeDialog {
            val fragment = DateTimeDialog()
            fragment.arguments = bundleOf(
                REQUEST_KEY to requestKey,
                MESSAGE to message,
                DATE to date
            )
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val args = requireArguments()
        val requestKey = args.getString(REQUEST_KEY)!!
        val calendar = Calendar.getInstance()
        @Suppress("DEPRECATION")
        calendar.time = args.getSerializable(DATE) as Date

        val inflater = requireActivity().layoutInflater
        val binding = DialogDateTimeBinding.inflate(inflater, null, false)
        binding.message.text = requireArguments().getString(MESSAGE)

        with(binding.datePicker) {
            updateDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            setOnDateChangedListener { _, year, month, day ->
                calendar.set(year, month, day)
            }
        }

        with(binding.timePicker) {
            hour = calendar.get(Calendar.HOUR_OF_DAY)
            minute = calendar.get(Calendar.MINUTE)
            setOnTimeChangedListener { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
            }
        }

        binding.tabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> {
                            binding.datePicker.visibility = View.VISIBLE
                            binding.timePicker.visibility = View.GONE
                        }
                        1 -> {
                            binding.datePicker.visibility = View.GONE
                            binding.timePicker.visibility = View.VISIBLE
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            }
        )

        return AlertDialog.Builder(requireActivity()).apply {
            setView(binding.root)
            setPositiveButton(android.R.string.ok) { _, _ ->
                setFragmentResult(requestKey, bundleOf(DATE to calendar.time))
            }
            setNegativeButton(android.R.string.cancel, null)
        }.create()
    }
}