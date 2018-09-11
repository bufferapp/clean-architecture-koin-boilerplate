package org.buffer.android.boilerplate.remote.mapper

import org.buffer.android.boilerplate.remote.test.factory.BufferooFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class BufferooMapperTest {

    private val bufferooEntityMapper = BufferooEntityMapper()

    @Test
    fun mapFromRemoteMapsData() {
        val bufferooModel = BufferooFactory.makeBufferooModel()
        val bufferooEntity = bufferooEntityMapper.mapFromRemote(bufferooModel)

        assertEquals(bufferooModel.name, bufferooEntity.name)
        assertEquals(bufferooModel.title, bufferooEntity.title)
        assertEquals(bufferooModel.avatar, bufferooEntity.avatar)
    }
}