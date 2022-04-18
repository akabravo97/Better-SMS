package com.abhijeet.bettermessaging.view.messages

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.abhijeet.bettermessaging.repository.PagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessageListViewModel @Inject constructor(val repository: PagingRepository) : ViewModel() {
    val updateList = MutableLiveData(true)

    val currentMessage = repository.loadMessagePages().cachedIn(viewModelScope)

}