package org.buffer.android.boilerplate.data.browse.interactor

import io.reactivex.Flowable
import org.buffer.android.boilerplate.data.browse.Bufferoo
import org.buffer.android.boilerplate.data.executor.PostExecutionThread
import org.buffer.android.boilerplate.data.executor.ThreadExecutor
import org.buffer.android.boilerplate.data.interactor.FlowableUseCase
import org.buffer.android.boilerplate.data.repository.BufferooRepository

open class GetBufferoos(val bufferooRepository: BufferooRepository,
                        threadExecutor: ThreadExecutor,
                        postExecutionThread: PostExecutionThread):
        FlowableUseCase<List<Bufferoo>, Void?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Void?): Flowable<List<Bufferoo>> {
        return bufferooRepository.getBufferoos()
    }
}