package com.abhijeet.bettermessaging.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.abhijeet.bettermessaging.model.MessagePagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PagingRepository @Inject constructor(private val messageRepository: MessageRepository) {
    fun loadMessagePages() = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 100,
            enablePlaceholders = false
        ), pagingSourceFactory = { MessagePagingSource(messageRepository) }
    ).liveData
}