package com.abhijeet.bettermessaging.model

import androidx.paging.PagingSource
import com.abhijeet.bettermessaging.repository.MessageRepository

class MessagePagingSource constructor(
    val messageRepository: MessageRepository
) : PagingSource<Int, Message>() {
    companion object {
        private const val START = 1
    }

    var START_LOAD_SIZE: Int = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Message> {
        try {
            val incomingPageCount = params.key ?: 1

            if (params.key == null) {
                START_LOAD_SIZE = params.loadSize
            }

            val offsetCalc = {
                if (incomingPageCount == 2)
                    START_LOAD_SIZE
                else
                    ((incomingPageCount - 1) * params.loadSize) + (START_LOAD_SIZE - params.loadSize)
            }
            val offset = offsetCalc.invoke()

            val messages = messageRepository.getAllMessages(params.loadSize, offset)
            val count = messages.size

            return LoadResult.Page(
                data = messages,
                prevKey = null,
                nextKey = if (count < params.loadSize) null else incomingPageCount + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error<Int, Message>(e)
        }
    }
}