package org.buffer.android.boilerplate.cache.mapper

import org.buffer.android.boilerplate.cache.model.CachedBufferoo
import org.buffer.android.boilerplate.data.browse.Bufferoo

/**
 * Map a [CachedBufferoo] instance to and from a [Bufferoo] instance when data is moving between
 * this later and the Data layer
 */
open class BufferooEntityMapper :
        EntityMapper<CachedBufferoo, Bufferoo> {

    /**
     * Map a [Bufferoo] instance to a [CachedBufferoo] instance
     */
    override fun mapToCached(type: Bufferoo): CachedBufferoo {
        return CachedBufferoo(type.id, type.name, type.title, type.avatar)
    }

    /**
     * Map a [CachedBufferoo] instance to a [Bufferoo] instance
     */
    override fun mapFromCached(type: CachedBufferoo): Bufferoo {
        return Bufferoo(type.id, type.name, type.title, type.avatar)
    }

}