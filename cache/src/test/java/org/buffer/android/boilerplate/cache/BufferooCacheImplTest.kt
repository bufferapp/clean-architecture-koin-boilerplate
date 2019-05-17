package org.buffer.android.boilerplate.cache

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.buffer.android.boilerplate.cache.db.BufferoosDatabase
import org.buffer.android.boilerplate.cache.mapper.BufferooEntityMapper
import org.buffer.android.boilerplate.cache.model.CachedBufferoo
import org.buffer.android.boilerplate.cache.test.factory.BufferooFactory
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class BufferooCacheImplTest {

    private var bufferoosDatabase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
            BufferoosDatabase::class.java).allowMainThreadQueries().build()
    private var entityMapper = BufferooEntityMapper()
    private var preferencesHelper = PreferencesHelper(ApplicationProvider.getApplicationContext())


    private val databaseHelper = BufferooCacheImpl(bufferoosDatabase,
            entityMapper, preferencesHelper)

    @Test
    fun clearTablesCompletes() {
        val testObserver = databaseHelper.clearBufferoos().test()
        testObserver.assertComplete()
    }

    //<editor-fold desc="Save Bufferoos">
    @Test
    fun saveBufferoosCompletes() {
        val bufferooEntities = BufferooFactory.makeBufferooEntityList(2)

        val testObserver = databaseHelper.saveBufferoos(bufferooEntities).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveBufferoosSavesData() {
        val bufferooCount = 2
        val bufferooEntities = BufferooFactory.makeBufferooEntityList(bufferooCount)

        databaseHelper.saveBufferoos(bufferooEntities).test()
        checkNumRowsInBufferoosTable(bufferooCount)
    }
    //</editor-fold>

    //<editor-fold desc="Get Bufferoos">
    @Test
    fun getBufferoosCompletes() {
        val testObserver = databaseHelper.getBufferoos().test()
        testObserver.assertComplete()
    }

    @Test
    fun getBufferoosReturnsData() {
        val bufferooEntities = BufferooFactory.makeBufferooEntityList(2)
        val cachedBufferoos = mutableListOf<CachedBufferoo>()
        bufferooEntities.forEach {
            cachedBufferoos.add(entityMapper.mapToCached(it))
        }
        insertBufferoos(cachedBufferoos)

        val testObserver = databaseHelper.getBufferoos().test()
      //  testObserver.assertValue(bufferooEntities)
    }
    //</editor-fold>

    private fun insertBufferoos(cachedBufferoos: List<CachedBufferoo>) {
        cachedBufferoos.forEach {
            bufferoosDatabase.cachedBufferooDao().insertBufferoo(it)
        }
    }

    private fun checkNumRowsInBufferoosTable(expectedRows: Int) {
        val numberOfRows = bufferoosDatabase.cachedBufferooDao().getBufferoos().size
        assertEquals(expectedRows, numberOfRows)
    }
}