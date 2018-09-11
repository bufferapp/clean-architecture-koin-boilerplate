package org.buffer.android.boilerplate.remote

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.buffer.android.boilerplate.data.browse.Bufferoo
import org.buffer.android.boilerplate.data.source.BufferooDataStore
import org.buffer.android.boilerplate.remote.mapper.BufferooEntityMapper

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class BufferooRemoteImpl constructor(private val bufferooService: BufferooService,
                                     private val entityMapper: BufferooEntityMapper)
    :BufferooDataStore {

    /**
     * Retrieve a list of [Bufferoo] instances from the [BufferooService].
     */
    override fun getBufferoos(): Flowable<List<Bufferoo>> {
        return bufferooService.getBufferoos()
                .map { it.team }
                .map {
                    val entities = mutableListOf<Bufferoo>()
                    it.forEach { entities.add(entityMapper.mapFromRemote(it)) }
                    entities
                }
    }

    override fun clearBufferoos(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveBufferoos(bufferoos: List<Bufferoo>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isCached(): Single<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setLastCacheTime(lastCache: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isExpired(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}