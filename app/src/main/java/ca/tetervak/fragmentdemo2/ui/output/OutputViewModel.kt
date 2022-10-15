package ca.tetervak.fragmentdemo2.ui.output

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class OutputViewModel : ViewModel() {

    // check-in date-time
    private val _liveCheckInDate = MutableLiveData(Date())
    val liveCheckInDate: LiveData<Date> = _liveCheckInDate
    val checkInDate get() = liveCheckInDate.value!!
    fun setCheckInDate(date: Date){
        _liveCheckInDate.value = date
    }

    // check-in date-time
    private val _liveCheckOutDate = MutableLiveData(Date())
    val liveCheckOutDate: LiveData<Date> = _liveCheckOutDate
    val checkOutDate get() = liveCheckOutDate.value!!
    fun setCheckOutDate(date: Date){
        _liveCheckOutDate.value = date
    }
}