package com.lifeandme.data.di

import android.content.Context
import androidx.room.Room
import com.lifeandme.data.local.AppDatabase
import com.lifeandme.data.local.BirthdayDao
import com.lifeandme.data.repository.BirthdayRepositoryImpl
import com.lifeandme.domain.repository.BirthdayRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BirthdayDbModule {

    @Binds
    @Singleton
    abstract fun bindBirthdayRepository(impl: BirthdayRepositoryImpl): BirthdayRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideBirthdayDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "birthday.db"
        ).build()

    @Provides
    @Singleton
    fun provideBirthdayDao(appDatabase: AppDatabase): BirthdayDao = appDatabase.birthdayDao()
}