package com.abhijeet.bettermessaging.module

import android.content.Context
import com.abhijeet.bettermessaging.repository.MessageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object MessageModule {
    @Singleton
    @Provides
    fun provideMessageRep(@ApplicationContext applicationContext: Context): MessageRepository {
        return MessageRepository(applicationContext)
    }
}