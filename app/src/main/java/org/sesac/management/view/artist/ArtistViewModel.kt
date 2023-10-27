package org.sesac.management.view.artist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.sesac.management.data.local.Artist
import org.sesac.management.data.local.ArtistType
import org.sesac.management.data.local.Event
import org.sesac.management.data.local.Rate
import org.sesac.management.repository.ArtistRepository
import org.sesac.management.util.common.Referecne
import org.sesac.management.util.common.defaultDispatcher

class ArtistViewModel(private val repository: ArtistRepository) : ViewModel() {

    var getAllArtist = MutableLiveData<List<Artist>>()
    var getArtistDetail = MutableLiveData<Artist>()
    var getEventFromArtist = MutableLiveData<List<Event>>()
    var insertArtist = MutableLiveData<List<Long>>()
    private var _rateList = MutableLiveData<List<Rate>>()
    val rateList: LiveData<List<Rate>>
        get() = _rateList

    fun getAllArtist() {
        viewModelScope.launch {
            getAllArtist.value = repository.getAllArtist()
        }
    }

    private var _getAllSortedArtist = MutableLiveData<List<Artist>>() // 정렬된 아티스트 목록
    val getAllSortedArtist: LiveData<List<Artist>> get() = _getAllSortedArtist
    var rateReferecne: Referecne = Referecne.AVERAGE // 선택된 chip 정보

    /**
     * Get all sorted artist, 정해진 Rate항목으로 Artist 목록을 정렬해주는 메서드
     *
     * 데이터 양이 많을 수도 있으므로 Dispatcher.Default를 사용했다.
     * @param reference : 정렬할 기준
     * @param list : 정렬 대상
     * @author 진혁
     */
    fun getAllSortedArtist(reference: Referecne, list: List<Artist>) {
        rateReferecne = reference // graph 에 데이터를 입력할 때 필요함

        viewModelScope.launch(defaultDispatcher) {
            when (reference) {
                Referecne.AVERAGE -> {
                    _getAllSortedArtist.postValue(list.sortedByDescending {
                        it.rate?.average ?: 0f
                    })
                }

                Referecne.INCOME -> {
                    _getAllSortedArtist.postValue(list.sortedByDescending {
                        it.rate?.income?.toFloat() ?: 0f
                    })
                }

                Referecne.POPULARITY -> {
                    _getAllSortedArtist.postValue(list.sortedByDescending {
                        it.rate?.popularity?.toFloat() ?: 0f
                    })
                }

                Referecne.SING -> {
                    _getAllSortedArtist.postValue(list.sortedByDescending {
                        it.rate?.sing?.toFloat() ?: 0f
                    })
                }

                Referecne.DANCE -> {
                    _getAllSortedArtist.postValue(list.sortedByDescending {
                        it.rate?.dance?.toFloat() ?: 0f
                    })
                }

                Referecne.PERFORMACE -> {
                    _getAllSortedArtist.postValue(list.sortedByDescending {
                        it.rate?.performance?.toFloat() ?: 0f
                    })
                }

                else -> {
                    _getAllSortedArtist.value = listOf<Artist>()
                }
            }
        }
    }

    fun insertArtist(artist: Artist) {
        CoroutineScope(Dispatchers.Main).launch {
            insertArtist.value = repository.insertArtist(artist)
        }
    }

    fun insertRateWithArtist(rate: Rate, artistId: Int) = viewModelScope.launch {
        repository.insertRateWithArtist(rate, artistId)
    }

    fun updateArtist(artist: Artist) {
        repository.updateArtist(artist)
    }

    fun getSearchResult(keyword: String) {
        CoroutineScope(Dispatchers.Main).launch {
            getAllArtist.value = repository.getArtistByName(keyword)
        }
    }

    fun getArtistByType(type: ArtistType) {
        CoroutineScope(Dispatchers.Main).launch {
            getAllArtist.value = repository.getArtistByType(type)
        }
    }

    fun getArtistById(id: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            getArtistDetail.value = repository.getArtistById(id)
        }
    }

    fun getEventFromArtistId(artistId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            getEventFromArtist = repository.getEventFromArtist(artistId)
        }
    }

    fun deleteArtist(artist: Artist) {
        CoroutineScope(Dispatchers.Main).launch {
            repository.deleteArtist(artist)
        }
    }

    class ArtistViewModelFactory(private val artistRepository: ArtistRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(ArtistViewModel::class.java)) {
                ArtistViewModel(artistRepository) as T
            } else {
                throw IllegalArgumentException()
            }
        }
    }
}
