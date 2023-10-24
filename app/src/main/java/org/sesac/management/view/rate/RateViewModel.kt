package org.sesac.management.view.rate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.sesac.management.data.room.Rate
import org.sesac.management.repository.ArtistRepository
import org.sesac.management.view.event.EventViewModel

class RateViewModel(private val artistRepository: ArtistRepository) : ViewModel() {

    private var _rateList = MutableLiveData<List<Rate>>()
    val rateList: LiveData<List<Rate>>
        get() = _rateList

    fun insertRate(rate: Rate) = viewModelScope.launch {
        artistRepository.insertRate(rate)
    }

    fun updateRate(rateId: Int, artistId: Int) = viewModelScope.launch {
        artistRepository.updateRate(rateId, artistId)
    }

    fun getAllRate() = viewModelScope.launch {
        _rateList.value = artistRepository.getAllRate()
    }

    fun getRate(rateId: Int) = viewModelScope.launch {
        artistRepository.getRate(rateId)
    }

    class RateViewModelFactory(private val artistRepository: ArtistRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
                RateViewModel(artistRepository) as T
            } else {
                throw IllegalArgumentException()
            }
        }
    }
}