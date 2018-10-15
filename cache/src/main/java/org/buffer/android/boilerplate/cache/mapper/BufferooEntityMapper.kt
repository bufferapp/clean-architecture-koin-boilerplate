package org.buffer.android.boilerplate.cache.mapper

import org.buffer.android.boilerplate.cache.model.CachedBufferoo
import org.buffer.android.boilerplate.data.browse.Bufferoo

/**
 * Map a [CachedBufferoo] instance to and from a [Bufferoo] instance when data is moving between
 * this later and the Data layer
 */

/**
 * Map a [Bufferoo] instance to a [CachedBufferoo] instance
 */
fun Bufferoo.mapToCached(): CachedBufferoo {
    return CachedBufferoo(this.id, this.name, this.title, this.avatar)
}

/**
 * Map a [CachedBufferoo] instance to a [Bufferoo] instance
 */
fun CachedBufferoo.mapFromCached(): Bufferoo {
    return Bufferoo(this.id, this.name, this.title, this.avatar)
}