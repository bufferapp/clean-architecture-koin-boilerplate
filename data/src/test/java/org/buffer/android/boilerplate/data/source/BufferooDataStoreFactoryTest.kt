package org.buffer.android.boilerplate.data.source

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BufferooDataStoreFactoryTest {

    private val bufferooCacheDataStore = mock<BufferooDataStore>()
    private val bufferooRemoteDataStore = mock<BufferooDataStore>()

    private val bufferooDataStoreFactory = BufferooDataStoreFactory(
            bufferooCacheDataStore, bufferooRemoteDataStore)

    //<editor-fold desc="Retrieve Data Store">
    @Test
    fun retrieveDataStoreWhenNotCachedReturnsRemoteDataStore() {
        stubBufferooCacheIsCached(Single.just(false))
        val bufferooDataStore = bufferooDataStoreFactory.retrieveDataStore(false)
        assertTrue(bufferooDataStore == bufferooRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreWhenCacheExpiredReturnsRemoteDataStore() {
        stubBufferooCacheIsCached(Single.just(true))
        stubBufferooCacheIsExpired(true)
        val bufferooDataStore = bufferooDataStoreFactory.retrieveDataStore(true)
        assertTrue(bufferooDataStore  == bufferooRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreReturnsCacheDataStore() {
        stubBufferooCacheIsCached(Single.just(true))
        stubBufferooCacheIsExpired(false)
        val bufferooDataStore = bufferooDataStoreFactory.retrieveDataStore(true)
        assertTrue(bufferooDataStore  == bufferooCacheDataStore)
    }
    //</editor-fold>

    //<editor-fold desc="Retrieve Remote Data Store">
    @Test
    fun retrieveRemoteDataStoreReturnsRemoteDataStore() {
        val bufferooDataStore = bufferooDataStoreFactory.retrieveRemoteDataStore()
        assertTrue(bufferooDataStore  == bufferooRemoteDataStore)
    }
    //</editor-fold>

    //<editor-fold desc="Retrieve Cache Data Store">
    @Test
    fun retrieveCacheDataStoreReturnsCacheDataStore() {
        val bufferooDataStore = bufferooDataStoreFactory.retrieveCacheDataStore()
        assertTrue(bufferooDataStore  == bufferooCacheDataStore)
    }
    //</editor-fold>

    //<editor-fold desc="Stub helper methods">
    private fun stubBufferooCacheIsCached(single: Single<Boolean>) {
        whenever(bufferooCacheDataStore.isCached())
                .thenReturn(single)
    }

    private fun stubBufferooCacheIsExpired(isExpired: Boolean) {
        whenever(bufferooCacheDataStore.isExpired())
                .thenReturn(isExpired)
    }
    //</editor-fold>

}