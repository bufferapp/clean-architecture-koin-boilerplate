package org.buffer.android.boilerplate.remote.mapper

import org.buffer.android.boilerplate.data.browse.Bufferoo
import org.buffer.android.boilerplate.remote.model.BufferooModel

/**
 * Map a [BufferooModel] to and from a [Bufferoo] instance when data is moving between
 * this later and the Data layer
 *
 * Map an instance of a [BufferooModel] to a [Bufferoo] model
 */
fun BufferooModel.mapFromRemote(): Bufferoo {
    return Bufferoo(this.id, this.name, this.title, this.avatar)
}