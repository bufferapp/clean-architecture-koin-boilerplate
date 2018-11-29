package org.buffer.android.boilerplate.ui.di

import androidx.room.Room
import org.buffer.android.boilerplate.cache.BufferooCacheImpl
import org.buffer.android.boilerplate.cache.PreferencesHelper
import org.buffer.android.boilerplate.cache.db.BufferoosDatabase
import org.buffer.android.boilerplate.cache.mapper.BufferooEntityMapper
import org.buffer.android.boilerplate.data.BufferooDataRepository
import org.buffer.android.boilerplate.data.browse.interactor.GetBufferoos
import org.buffer.android.boilerplate.data.executor.JobExecutor
import org.buffer.android.boilerplate.data.executor.PostExecutionThread
import org.buffer.android.boilerplate.data.executor.ThreadExecutor
import org.buffer.android.boilerplate.data.repository.BufferooRepository
import org.buffer.android.boilerplate.data.source.BufferooDataStore
import org.buffer.android.boilerplate.data.source.BufferooDataStoreFactory
import org.buffer.android.boilerplate.remote.BufferooRemoteImpl
import org.buffer.android.boilerplate.remote.BufferooServiceFactory
import org.buffer.android.boilerplate.ui.BuildConfig
import org.buffer.android.boilerplate.ui.UiThread
import org.buffer.android.boilerplate.ui.browse.BrowseAdapter
import org.buffer.android.boilerplate.ui.browse.BrowseBufferoosViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val applicationModule = module(override=true) {

    single { PreferencesHelper(androidContext()) }

    factory { org.buffer.android.boilerplate.remote.mapper.BufferooEntityMapper() }

    single { JobExecutor() as ThreadExecutor }
    single { UiThread() as PostExecutionThread }

    single { Room.databaseBuilder(androidContext(),
            BufferoosDatabase::class.java, "bufferoos.db")
            .build() }
    factory { get<BufferoosDatabase>().cachedBufferooDao() }

    factory<BufferooDataStore>("remote") { BufferooRemoteImpl(get(), get()) }
    factory<BufferooDataStore>("local") { BufferooCacheImpl(get(), get(), get()) }
    factory { BufferooDataStoreFactory(get("local"), get("remote")) }

    factory { BufferooEntityMapper() }
    factory { BufferooServiceFactory.makeBuffeoorService(BuildConfig.DEBUG) }

    factory<BufferooRepository> { BufferooDataRepository(get()) }
}

val browseModule = module("Browse", override = true) {
    factory { BrowseAdapter() }
    factory { GetBufferoos(get(), get(), get()) }
    viewModel { BrowseBufferoosViewModel(get()) }
}